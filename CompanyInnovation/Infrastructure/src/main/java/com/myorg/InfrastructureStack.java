package com.myorg;

import software.amazon.awscdk.Duration;
import software.amazon.awscdk.customresources.*;
import software.amazon.awscdk.services.apigateway.*;
import software.amazon.awscdk.services.cognito.*;
import software.amazon.awscdk.RemovalPolicy;
import software.amazon.awscdk.services.apigateway.LambdaIntegration;
import software.amazon.awscdk.services.apigateway.LambdaRestApi;
import software.amazon.awscdk.services.dynamodb.*;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.Runtime;
import software.constructs.Construct;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// import software.amazon.awscdk.Duration;
// import software.amazon.awscdk.services.sqs.Queue;
import java.awt.geom.AffineTransform;
import java.util.HashMap;
import java.util.Map;

public class InfrastructureStack extends Stack {
    public InfrastructureStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public InfrastructureStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        //Database
        TableProps.Builder tablePropsBuilder = TableProps.builder()
                .tableName("innovations")
                .partitionKey(Attribute.builder()
                        .name("userId")
                        .type(AttributeType.STRING)
                        .build())
                .encryption(TableEncryption.DEFAULT)
                .billingMode(BillingMode.PAY_PER_REQUEST)
                .removalPolicy(RemovalPolicy.RETAIN);

        Table table = new Table(this, "InnovationTable", tablePropsBuilder.build());

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

        CfnUserPoolUser cfnUserPoolUser =new CfnUserPoolUser(this, "engineeringLead",
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
               Function.Builder.create(this, "hello_world_handler")
                .runtime(Runtime.JAVA_11)
                .handler("com.innovation.getInnovation.controller.LambdaHandler")
                .memorySize(512)
                .timeout(Duration.seconds(10))
                .functionName("handleRequest")
                .code(Code.fromAsset("../assets/GetInnovation.jar"))
                .build();

        Function createInnovationFunction =
                Function.Builder.create(this,"lambdaCreate")
                .runtime(Runtime.JAVA_11)
                .handler("com.innovation.createInnovation.LamdaHandler")
                .memorySize(1024)
                .timeout(Duration.seconds(30))
                .functionName("lambdaCreate")
                .code(Code.fromAsset("../assets/SubmitInnovation.jar"))
                .build();

        LambdaRestApi gateway = LambdaRestApi.Builder.create(this, "gateway")
                .handler(getInnovationFunction)
//                .defaultMethodOptions(MethodOptions.builder().authorizationType(AuthorizationType.COGNITO).authorizer(auth).build())
                .build();

        gateway.getRoot().addResource("getInnovation").addMethod("GET", new LambdaIntegration(getInnovationFunction), MethodOptions.builder().build());

        //gateway.getRoot().addResource("submit").addMethod("GET", new LambdaIntegration(createInnovationFunction));

        gateway.getRoot().addResource("submit").addMethod("POST", new LambdaIntegration(createInnovationFunction), MethodOptions.builder().build());
    }
}
