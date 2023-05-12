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
                .handler("org.example.controller.SubmitController")
                .memorySize(1024)
                .timeout(Duration.seconds(20))
                .functionName("LambdaCreate")
                .code(Code.fromAsset("C:\\Users\\PC\\Desktop\\Innovation_Internship\\CompanyInnovation\\functions\\createInnovation\\target\\createInnovation-0.0.1-SNAPSHOT.jar"))
                .build();






    }
}
