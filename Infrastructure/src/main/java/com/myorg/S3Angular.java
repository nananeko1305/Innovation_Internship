package com.myorg;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.constructs.Construct;

public class S3Angular extends Stack {

    public S3Angular(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public S3Angular(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        System.out.println("HelloWorld");
    }

}
