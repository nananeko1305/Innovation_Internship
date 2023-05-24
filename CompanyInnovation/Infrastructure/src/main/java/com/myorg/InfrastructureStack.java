package com.myorg;

import software.amazon.awscdk.Duration;
import software.amazon.awscdk.RemovalPolicy;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.customresources.*;
import software.amazon.awscdk.services.apigateway.*;
import software.amazon.awscdk.services.cognito.*;
import software.amazon.awscdk.services.dynamodb.*;
import software.amazon.awscdk.services.iam.Effect;
import software.amazon.awscdk.services.iam.PolicyStatement;
import software.amazon.awscdk.services.lambda.CfnFunction;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.ses.EmailIdentity;
import software.amazon.awscdk.services.ses.Identity;
import software.constructs.Construct;

import java.util.*;

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
                        .name("id")
                        .type(AttributeType.STRING)
                        .build())
                .encryption(TableEncryption.DEFAULT)
                .billingMode(BillingMode.PAY_PER_REQUEST)
                .removalPolicy(RemovalPolicy.DESTROY);

        GlobalSecondaryIndexProps.Builder gsiPropsBuilder = GlobalSecondaryIndexProps.builder()
                .indexName("userIdIndex")
                .partitionKey(Attribute.builder()
                        .name("userId")
                        .type(AttributeType.STRING)
                        .build())
                .projectionType(ProjectionType.ALL);

        Table innovationsTable = new Table(this, "InnovationTable", tablePropsBuilder.build());
        innovationsTable.addGlobalSecondaryIndex(gsiPropsBuilder.build());

        TableProps.Builder tablePropsBuilder1 = TableProps.builder()
                .tableName("products")
                .partitionKey(Attribute.builder()
                        .name("id")
                        .type(AttributeType.STRING)
                        .build())
                .encryption(TableEncryption.DEFAULT)
                .billingMode(BillingMode.PAY_PER_REQUEST)
                .removalPolicy(RemovalPolicy.DESTROY);

        TableProps.Builder tablePropsBuilder2 = TableProps.builder()
                .tableName("usersTokens")
                .partitionKey(Attribute.builder()
                        .name("userId")
                        .type(AttributeType.STRING)
                        .build())
                .encryption(TableEncryption.DEFAULT)
                .billingMode(BillingMode.PAY_PER_REQUEST)
                .removalPolicy(RemovalPolicy.DESTROY);

        Table productTable= new Table(this , "ProductTable" , tablePropsBuilder1.build());
        Table userTokensTable= new Table(this , "UserTokensTable" , tablePropsBuilder2.build());

//
        UserPool pool = UserPool.Builder.create(this, "Pool")
                .selfSignUpEnabled(true)
                .userVerification(UserVerificationConfig.builder()

                        .emailSubject("Verify your email for our innovation app!")
                        .emailBody("Thanks for signing up to our innovation! Your verification code is {####}")
                        .emailStyle(VerificationEmailStyle.CODE)
                        .build())
                .autoVerify(AutoVerifiedAttrs.builder().email(true).build())
                .standardAttributes(StandardAttributes.builder().givenName(StandardAttribute.builder().required(Boolean.TRUE).build()).build())
                .build();
        pool.addClient("angular-cognito");

        new CfnUserPoolGroup(this, "Lead", CfnUserPoolGroupProps.builder().groupName("Lead")
                .userPoolId(pool.getUserPoolId())
                .build());

        new CfnUserPoolGroup(this, "Employee", CfnUserPoolGroupProps.builder().groupName("Employee")
                .userPoolId(pool.getUserPoolId())
                .build());

        new CfnUserPoolGroup(this, "Admin", CfnUserPoolGroupProps.builder().groupName("Admin")
                .userPoolId(pool.getUserPoolId())
                .build());

        Object clientMetadata;

        List<String> deliveryMediumsList = new ArrayList<>();
        deliveryMediumsList.add("EMAIL");
        List<CfnUserPoolUser.AttributeTypeProperty> attributesList = new ArrayList<CfnUserPoolUser.AttributeTypeProperty>();
        attributesList.add(CfnUserPoolUser.AttributeTypeProperty.builder().name("email").value("innovation.lead@outlook.com").build());
        attributesList.add(CfnUserPoolUser.AttributeTypeProperty.builder().name("email_verified").value("True").build());
        attributesList.add(CfnUserPoolUser.AttributeTypeProperty.builder().name("given_name").value("EmployeeName EmployeeSurname").build());

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

        List<String> deliveryMediumsAdminList = new ArrayList<>();
        deliveryMediumsAdminList.add("EMAIL");
        List<CfnUserPoolUser.AttributeTypeProperty> attributesAdminList = new ArrayList<CfnUserPoolUser.AttributeTypeProperty>();
        attributesAdminList.add(CfnUserPoolUser.AttributeTypeProperty.builder().name("email").value("nananeko1305@hotmail.com").build());
        attributesAdminList.add(CfnUserPoolUser.AttributeTypeProperty.builder().name("email_verified").value("True").build());
        attributesAdminList.add(CfnUserPoolUser.AttributeTypeProperty.builder().name("given_name").value("AdminName AdminSurname").build());

        CfnUserPoolUser cfnUserPoolUserAdmin = new CfnUserPoolUser(this, "admin",
                CfnUserPoolUserProps.builder().userPoolId(pool.getUserPoolId())
                        .username("admin")
                        .desiredDeliveryMediums(deliveryMediumsAdminList)
                        .userAttributes(attributesAdminList)
                        .build());


        Map<String, Object> parametersMapAdmin = new HashMap<String, Object>() {{
            put("UserPoolId", pool.getUserPoolId());
            put("Username", cfnUserPoolUserAdmin.getUsername());
            put("Password", "Test1test1!");
            put("Permanent", true);
        }};
        AwsCustomResource awsCustomAdmin = AwsCustomResource.Builder.create(this, "aws-custom2")
                .onCreate(AwsSdkCall.builder()
                        .service("CognitoIdentityServiceProvider")
                        .action("adminSetUserPassword")
                        .parameters(parametersMapAdmin)
                        .physicalResourceId(PhysicalResourceId.of("AwsCustomResource-ForcePassword-${username}"))
                        .build())
                .policy(AwsCustomResourcePolicy.fromSdkCalls(SdkCallsPolicyOptions.builder().resources(AwsCustomResourcePolicy.ANY_RESOURCE).build()))
                .build();

        awsCustomAdmin.getNode().addDependency(cfnUserPoolUserAdmin);

        CfnUserPoolUserToGroupAttachment attach2 = new CfnUserPoolUserToGroupAttachment(this, "AttachUserToGroup2", CfnUserPoolUserToGroupAttachmentProps.builder()
                .userPoolId(pool.getUserPoolId())
                .groupName("Admin")
                .username("admin")
                .build());

        attach2.getNode().addDependency(cfnUserPoolUserAdmin);

//        CognitoUserPoolsAuthorizer auth = CognitoUserPoolsAuthorizer.Builder.create(this, "innovationAuthorizer")
//                .cognitoUserPools(poolsList)
//                .build();

        //create lambda to get innovations
        Function getInnovationFunction =
                Function.Builder.create(this, "GetInnovations")
                        .runtime(Runtime.JAVA_11)
                        .handler("com.innovation.getInnovation.controller.LambdaHandler")
                        .memorySize(512)
                        .timeout(Duration.seconds(10))
                        .code(Code.fromAsset("../assets/GetInnovation.jar"))
                        .functionName("GetInnovations")
                        .build();

                CfnFunction getInnoSnap = (CfnFunction)  getInnovationFunction.getNode().getDefaultChild();
                getInnoSnap.setSnapStart(CfnFunction.SnapStartProperty.builder().applyOn("PublishedVersions").build());

        Function createInnovationFunction =
                Function.Builder.create(this, "lambdaCreate")
                        .runtime(Runtime.JAVA_11)
                        .handler("com.innovation.createInnovation.LamdaHandler")
                        .memorySize(1024)
                        .timeout(Duration.seconds(30))
                        .functionName("lambdaCreate")
                        .code(Code.fromAsset("../assets/SubmitInnovation.jar"))
                        .build();

        Function manageShopFunction =
                Function.Builder.create(this, "manageShop")
                        .runtime(Runtime.JAVA_11)
                        .handler("com.innovation.manageShop.LambdaHandler")
                        .memorySize(1024)
                        .timeout(Duration.seconds(30))
                        .functionName("manageShop")
                        .code(Code.fromAsset("../assets/ManageShop.jar"))
                        .build();

        Function acceptDeclineFunction =
                Function.Builder.create(this, "lambdaAcceptDecline")
                        .runtime(Runtime.JAVA_11)
                        .handler("com.innovation.acceptOrDecline.LambdaHandler")
                        .memorySize(1024)
                        .functionName("AcceptDeclineLambda")
                        .timeout(Duration.seconds(50))
                        .code(Code.fromAsset("../assets/AcceptDeclineLambda.jar"))
                        .build();

        Function tokenShopFunction =
                Function.Builder.create(this, "tokenShop")
                        .runtime(Runtime.JAVA_11)
                        .handler("com.innovation.tokenShop.LambdaHandler")
                        .memorySize(1024)
                        .functionName("TokenShopLambda")
                        .timeout(Duration.seconds(50))
                        .code(Code.fromAsset("../assets/TokenShop.jar"))
                        .build();

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

        acceptDeclineFunction.addToRolePolicy(PolicyStatement.Builder.create()
                .sid("PermisionToGetUser")
                .effect(Effect.ALLOW)
                .actions(Collections.singletonList("cognito-idp:AdminGetUser"))
                .resources(Collections.singletonList("*"))
                .build());

        Function addMembershipEmployee =
                Function.Builder.create(this, "addMembershipEmployee")
                        .runtime(Runtime.JAVA_11)
                        .handler("org.innovation.AddEmployeeMembership")
                        .memorySize(1024)
                        .timeout(Duration.seconds(30))
                        .functionName("addMembershipEmployee")
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
//                .defaultCorsPreflightOptions(CorsOptions.builder()
//                        .allowOrigins(Cors.ALL_ORIGINS)
//                        .allowMethods(Cors.ALL_METHODS)
//                        .build())
                .build();
//

        //Snap start
        getInnovationFunction.addEnvironment("AWS_LAMBDA_ENABLE_SNAP_START", "1");
        createInnovationFunction.addEnvironment("AWS_LAMBDA_ENABLE_SNAP_START", "1");
        acceptDeclineFunction.addEnvironment("AWS_LAMBDA_ENABLE_SNAP_START", "1");
        addMembershipEmployee.addEnvironment("AWS_LAMBDA_ENABLE_SNAP_START", "1");


        CfnFunction createInnoSnap = (CfnFunction) createInnovationFunction.getNode().getDefaultChild();
        createInnoSnap.setSnapStart(CfnFunction.SnapStartProperty.builder().applyOn("PublishedVersions").build());

        CfnFunction acceptDeclineInnoSnap = (CfnFunction) acceptDeclineFunction.getNode().getDefaultChild();
        acceptDeclineInnoSnap.setSnapStart(CfnFunction.SnapStartProperty.builder().applyOn("PublishedVersions").build());

        CfnFunction addMembershipSnap = (CfnFunction) addMembershipEmployee.getNode().getDefaultChild();
        addMembershipSnap.setSnapStart(CfnFunction.SnapStartProperty.builder().applyOn("PublishedVersions").build());

        //Snap start start end


        List<String> CORSoriginsList = new ArrayList<String>();
        CORSoriginsList.add("*");

        List<String> CORSmethodsListSubmit = new ArrayList<String>();
        CORSmethodsListSubmit.add("POST");
        CORSmethodsListSubmit.add("OPTIONS");


        List<String> CORSmethodsListGet = new ArrayList<String>();
        CORSmethodsListGet.add("GET");
        CORSmethodsListGet.add("OPTIONS");

        List<String> CORSmethodsListGetAccDec = new ArrayList<String>();
        CORSmethodsListGetAccDec.add("PUT");
        CORSmethodsListGetAccDec.add("OPTIONS");

        List<String> CORSheadersList = new ArrayList<String>();
        CORSheadersList.add("Content-Type");
        CORSheadersList.add("X-Amz-Date");
        CORSheadersList.add("Authorization");
        CORSheadersList.add("X-Api-Key");
        CORSheadersList.add("Authorization");
        CORSheadersList.add("X-Amz-Security-Token");
        CORSheadersList.add("jwttoken");

        //API GATEWAY
        gateway.getRoot().addResource("innovations").addMethod("GET", new LambdaIntegration(getInnovationFunction.getCurrentVersion()), MethodOptions.builder().authorizationType(AuthorizationType.IAM).build());
        gateway.getRoot().getResource("innovations")
                .addCorsPreflight(CorsOptions.builder()
                .allowOrigins(CORSoriginsList)
                .allowMethods(CORSmethodsListGet)
                .allowHeaders(CORSheadersList)
                .build())
        ;
        gateway.getRoot().addResource("submit").addMethod("POST", new LambdaIntegration(createInnovationFunction.getCurrentVersion()), MethodOptions.builder().authorizationType(AuthorizationType.IAM).build());
        gateway.getRoot().getResource("submit")
                .addCorsPreflight(CorsOptions.builder()
                .allowOrigins(CORSoriginsList)
                .allowMethods(CORSmethodsListSubmit)
                .allowHeaders(CORSheadersList)
                .build())
        ;


        gateway.getRoot().addResource("acceptDeclineInnovation").addMethod("PUT", new LambdaIntegration(acceptDeclineFunction.getCurrentVersion()), MethodOptions.builder().authorizationType(AuthorizationType.IAM).build());
        gateway.getRoot().getResource("acceptDeclineInnovation")
                .addCorsPreflight(CorsOptions.builder()
                .allowOrigins(CORSoriginsList)
                .allowMethods(CORSmethodsListGetAccDec)
                .allowHeaders(CORSheadersList)
                .build())
        ;



        Resource product = gateway.getRoot().addResource("product");
        product.addMethod("GET", new LambdaIntegration(manageShopFunction.getCurrentVersion()), MethodOptions.builder().authorizationType(AuthorizationType.IAM).build());
        product.addMethod("POST", new LambdaIntegration(manageShopFunction.getCurrentVersion()), MethodOptions.builder().authorizationType(AuthorizationType.IAM).build());
        product.addMethod("PUT", new LambdaIntegration(manageShopFunction.getCurrentVersion()), MethodOptions.builder().authorizationType(AuthorizationType.IAM).build());
        product.addMethod("DELETE", new LambdaIntegration(manageShopFunction.getCurrentVersion()), MethodOptions.builder().authorizationType(AuthorizationType.IAM).build());
        gateway.getRoot().getResource("product");

        Resource tokens = gateway.getRoot().addResource("tokens");
        tokens.addMethod("GET", new LambdaIntegration(tokenShopFunction.getCurrentVersion()), MethodOptions.builder().authorizationType(AuthorizationType.IAM).build());
        tokens.addMethod("POST", new LambdaIntegration(tokenShopFunction.getCurrentVersion()), MethodOptions.builder().authorizationType(AuthorizationType.IAM).build());
        gateway.getRoot().getResource("tokens");










        EmailIdentity identity = EmailIdentity.Builder.create(this, "Identity")
                .identity(Identity.email("compani.innovation.dept@outlook.com" ))
               .build();

        EmailIdentity identity1 = EmailIdentity.Builder.create(this, "Identity1")
                .identity(Identity.email("innovation.employee@outlook.com"))
                .build();
        EmailIdentity identity2 = EmailIdentity.Builder.create(this, "Identity2")
                .identity(Identity.email("inovationEmployee@outlook.com"))
                .build();
        EmailIdentity identity3 = EmailIdentity.Builder.create(this, "Identity3")
                .identity(Identity.email("innovation.lead@outlook.com"))
                .build();

        EmailIdentity identity4 = EmailIdentity.Builder.create(this, "Identity4")
                .identity(Identity.email("nananeko1305@hotmail.com"))
                .build();









    }
}