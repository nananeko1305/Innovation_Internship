package com.myorg;

import com.amazonaws.services.simpleemail.model.VerifyEmailIdentityRequest;
import software.amazon.awscdk.Duration;
import software.amazon.awscdk.RemovalPolicy;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.customresources.*;
import software.amazon.awscdk.services.apigateway.LambdaIntegration;
import software.amazon.awscdk.services.apigateway.LambdaRestApi;
import software.amazon.awscdk.services.apigateway.MethodOptions;
import software.amazon.awscdk.services.cognito.*;
import software.amazon.awscdk.services.dynamodb.*;
import software.amazon.awscdk.services.iam.Effect;
import software.amazon.awscdk.services.iam.PolicyStatement;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.ses.EmailIdentity;
import software.amazon.awscdk.services.ses.Identity;
import software.amazon.awssdk.services.ses.SesClient;
import software.constructs.Construct;

import java.util.*;

public class InfrastructureStack2 extends Stack {
    public InfrastructureStack2(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public InfrastructureStack2(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        //Database
        TableProps.Builder tablePropsBuilder = TableProps.builder()
                .tableName("innovations")
                .partitionKey(Attribute.builder()
                        .name("id")
                        .type(AttributeType.STRING)
                        .build())
                .encryption(TableEncryption.DEFAULT)
                .billingMode(BillingMode.PAY_PER_REQUEST)
                .removalPolicy(RemovalPolicy.DESTROY);

        Table table = new Table(this, "InnovationTable1", tablePropsBuilder.build());

        UserPool pool = UserPool.Builder.create(this, "Pool")
                .selfSignUpEnabled(true)
                .userVerification(UserVerificationConfig.builder()
                        .emailSubject("Verify your email for our innovation app!")
                        .emailBody("Thanks for signing up to our innovation! Your verification code is {####}")
                        .emailStyle(VerificationEmailStyle.CODE)
                        .build())
                .autoVerify(AutoVerifiedAttrs.builder().email(true).build())
                .build();
        pool.addClient("angular-cognito");

        new CfnUserPoolGroup(this, "Lead", CfnUserPoolGroupProps.builder().groupName("Lead")
                .userPoolId(pool.getUserPoolId())
                .build());

        new CfnUserPoolGroup(this, "Employee", CfnUserPoolGroupProps.builder().groupName("Employee")
                .userPoolId(pool.getUserPoolId())
                .build());

        Object clientMetadata;

        List<String> deliveryMediumsList = new ArrayList<>();
        deliveryMediumsList.add("EMAIL");
        List<CfnUserPoolUser.AttributeTypeProperty> attributesList = new ArrayList<CfnUserPoolUser.AttributeTypeProperty>();
        attributesList.add(CfnUserPoolUser.AttributeTypeProperty.builder().name("email").value("innovation.lead@outlook.com").build());
        attributesList.add(CfnUserPoolUser.AttributeTypeProperty.builder().name("email_verified").value("True").build());

        CfnUserPoolUser cfnUserPoolUser = new CfnUserPoolUser(this, "engineeringLead",
                CfnUserPoolUserProps.builder().userPoolId(pool.getUserPoolId())
                        .username("engineeringLead")
                        .desiredDeliveryMediums(deliveryMediumsList)
                        .userAttributes(attributesList)
                        .build());


        Map<String, Object> parametersMap = new HashMap<String, Object>() {{
            put("UserPoolId", pool.getUserPoolId());
            put("Username", cfnUserPoolUser.getUsername());
            put("Password", "Test1test1!");
            put("Permanent", true);
        }};
        AwsCustomResource awsCustom = AwsCustomResource.Builder.create(this, "aws-custom")
                .onCreate(AwsSdkCall.builder()
                        .service("CognitoIdentityServiceProvider")
                        .action("adminSetUserPassword")
                        .parameters(parametersMap)
                        .physicalResourceId(PhysicalResourceId.of("AwsCustomResource-ForcePassword-${username}"))
                        .build())
                .policy(AwsCustomResourcePolicy.fromSdkCalls(SdkCallsPolicyOptions.builder().resources(AwsCustomResourcePolicy.ANY_RESOURCE).build()))
                .build();

        awsCustom.getNode().addDependency(cfnUserPoolUser);

        CfnUserPoolUserToGroupAttachment attach1 = new CfnUserPoolUserToGroupAttachment(this, "AttachUserToGroup", CfnUserPoolUserToGroupAttachmentProps.builder()
                .userPoolId(pool.getUserPoolId())
                .groupName("Lead")
                .username("engineeringLead")
                .build());

        attach1.getNode().addDependency(cfnUserPoolUser);

        List<IUserPool> poolsList = new ArrayList<>();
        poolsList.add(pool);

//        CognitoUserPoolsAuthorizer auth = CognitoUserPoolsAuthorizer.Builder.create(this, "innovationAuthorizer")
//                .cognitoUserPools(poolsList)
//                .build();

        //create lambda to get innovations
        Function getInnovationFunction =
                Function.Builder.create(this, "GetInnovations1")
                        .runtime(Runtime.JAVA_11)
                        .handler("com.innovation.getInnovation.controller.LambdaHandler")
                        .memorySize(512)
                        .timeout(Duration.seconds(10))
                        .code(Code.fromAsset("../assets/GetInnovation.jar"))
                        .functionName("GetInnovations1")
                        .build();

        Function createInnovationFunction =
                Function.Builder.create(this, "lambdaCreate1")
                        .runtime(Runtime.JAVA_11)
                        .handler("com.innovation.createInnovation.LamdaHandler")
                        .memorySize(1024)
                        .timeout(Duration.seconds(30))
                        .functionName("lambdaCreate1")
                        .code(Code.fromAsset("../assets/SubmitInnovation.jar"))
                        .build();

        Function acceptDeclineFunction =
                Function.Builder.create(this, "lambdaAcceptDecline1")
                        .runtime(Runtime.JAVA_11)
                        .handler("com.innovation.acceptOrDecline.LambdaHandler")
                        .memorySize(512)
                        .functionName("AcceptDeclineLambda1")
                        .timeout(Duration.seconds(30))
                        .code(Code.fromAsset("../assets/AcceptDeclineLambda.jar"))
                        .build();

        acceptDeclineFunction.addEnvironment("AWS_LAMBDA_ENABLE_SNAP_START", "1");

        //permission for lamdaCreate to use SES service
        createInnovationFunction.addToRolePolicy(PolicyStatement.Builder.create()
                .effect(Effect.ALLOW)
                .actions(Collections.singletonList("ses:SendEmail"))
                .resources(Collections.singletonList("arn:aws:ses:eu-north-1:696993701802:identity/*"))
                .build());


        acceptDeclineFunction.addToRolePolicy(PolicyStatement.Builder.create()
                .effect(Effect.ALLOW)
                .actions(Collections.singletonList("ses:SendEmail"))
                .resources(Collections.singletonList("arn:aws:ses:eu-north-1:696993701802:identity/*"))
                .build());

        Function addMembershipEmployee =
                Function.Builder.create(this, "addMembershipEmployee1")
                        .runtime(Runtime.JAVA_11)
                        .handler("org.innovation.AddEmployeeMembership")
                        .memorySize(1024)
                        .timeout(Duration.seconds(30))
                        .functionName("addMembershipEmployee1")
                        .code(Code.fromAsset("../assets/AddEmployeeMembership.jar"))
                        .build();

        addMembershipEmployee.addToRolePolicy(PolicyStatement.Builder.create()
                .sid("AllowAddingUserToGroup")
                .effect(Effect.ALLOW)
                .actions(Collections.singletonList("cognito-idp:AdminAddUserToGroup"))
                .resources(Collections.singletonList("*"))
                .build());


        pool.addTrigger(UserPoolOperation.POST_CONFIRMATION, addMembershipEmployee);

        LambdaRestApi gateway = LambdaRestApi.Builder.create(this, "gateway")
                .handler(getInnovationFunction)
//                .defaultMethodOptions(MethodOptions.builder().authorizationType(AuthorizationType.COGNITO).authorizer(auth).build())
                .build();


        //API GATEWAY
        gateway.getRoot().addResource("innovations").addMethod("GET", new LambdaIntegration(getInnovationFunction), MethodOptions.builder().build());
        gateway.getRoot().addResource("submit").addMethod("POST", new LambdaIntegration(createInnovationFunction), MethodOptions.builder().build());
        gateway.getRoot().addResource("acceptDeclineInnovation").addMethod("PUT", new LambdaIntegration(acceptDeclineFunction), MethodOptions.builder().build());


        //gateway.getRoot().addResource("submit").addMethod("GET", new LambdaIntegration(createInnovationFunction));
        //Ses email verify


        String emailAddress = "compani.innovation.dept@outlook.com";
        String region = "eu-north-1";


        Map<String, Object> paramMap = new HashMap<String, Object>() {{
            put("service: ", "SES");
            put("action: ", "verifyEmailIdentity");
            put("EmailAddress", emailAddress);
        }};



        VerifyEmailIdentityRequest request= new VerifyEmailIdentityRequest();
        request.setEmailAddress("compani.innovation.dept@outlook.com");

        final EmailIdentity identityBuilder = EmailIdentity.Builder.create(this, "Identity")
                .identity(Identity.email("compani.innovation.dept@outlook.com"))
                .build();


      // SesClient client= SesClient.builder()
             //   .region("eu-north-1")
             //   .build();
       // client.verifyEmailIdentity(request);





    }
}
