package com.myorg;

import software.amazon.awscdk.customresources.*;
import software.amazon.awscdk.services.cognito.*;
import software.constructs.Construct;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
// import software.amazon.awscdk.Duration;
// import software.amazon.awscdk.services.sqs.Queue;

public class TestSTStack extends Stack {
    public TestSTStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public TestSTStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

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


    }
}
