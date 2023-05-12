package com.myorg;
import software.amazon.awscdk.Duration;
import software.amazon.awscdk.customresources.*;
import software.amazon.awscdk.services.cognito.*;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.Runtime;
import software.constructs.Construct;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;

public class LambdaSubmitStack extends Stack {
    public LambdaSubmitStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public LambdaSubmitStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);


        Function.Builder.create(this,"lambdaCreate")
                .runtime(Runtime.JAVA_11)
                .handler("com.innovation.createInnovation.LamdaHandler")
                .memorySize(1024)
                .timeout(Duration.seconds(30))
                .functionName("lambdaCreate")
                .code(Code.fromAsset("../assets/SubmitInnovation.jar"))
                .build();






    }
}
