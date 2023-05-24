package com.myorg;

import software.amazon.awscdk.Duration;
import software.amazon.awscdk.RemovalPolicy;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.customresources.*;
import software.amazon.awscdk.services.apigateway.AuthorizationType;
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
import software.constructs.Construct;

import java.util.*;

public class TestStack extends Stack {
    public TestStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public TestStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);


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
        pool.addClient("test-client");

        CfnUserPool cfnUserPool = (CfnUserPool) pool.getNode().getDefaultChild();

        cfnUserPool.overrideLogicalId("abc");

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


    }
}
