package com.myorg;

import software.amazon.awscdk.services.cognito.UserPool;
import software.constructs.Construct;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
// import software.amazon.awscdk.Duration;
// import software.amazon.awscdk.services.sqs.Queue;

public class TestSTStack extends Stack {
    public TestSTStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public TestSTStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        UserPool pool = new UserPool(this, "Pool");
    }
}
