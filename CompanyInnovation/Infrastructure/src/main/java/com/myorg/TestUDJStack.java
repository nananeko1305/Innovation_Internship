package com.myorg;

import software.amazon.awscdk.Duration;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.constructs.Construct;
import software.amazon.awscdk.services.apigateway.*;
import software.amazon.awscdk.services.events.Rule;
import software.amazon.awscdk.services.events.Schedule;
import software.amazon.awscdk.services.lambda.*;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.sqs.Queue;
import software.amazon.awscdk.services.events.targets.*;
import software.amazon.awscdk.services.iam.*;

public class TestUDJStack extends Stack {

	public TestUDJStack(final Construct scope, final String id) {
		this(scope, id, null);
	}

	public TestUDJStack(final Construct scope, final String id, final StackProps props) {
		super(scope, id, props);

		// The code that defines your stack goes here

		// example resource
		// final Queue queue = Queue.Builder.create(this, "InfrastructureQueue")
		// .visibilityTimeout(Duration.seconds(300))
		// .build();
	}

	Object parameters;
	PolicyStatement policyStatement;
	AwsApi awsApi = AwsApi.Builder.create().action("action").service("service")
			// the properties below are optional
			.apiVersion("apiVersion").catchErrorPattern("catchErrorPattern").parameters(parameters)
			.policyStatement(policyStatement).build();

}