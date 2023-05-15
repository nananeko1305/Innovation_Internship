package com.myorg;

import software.amazon.awscdk.Duration;
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
                .removalPolicy(RemovalPolicy.RETAIN);

        Table table = new Table(this, "InnovationTable", tablePropsBuilder.build());


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

        LambdaRestApi gateway = LambdaRestApi.Builder.create(this, "gateway")
                .handler(getInnovationFunction)
                .build();

        gateway.getRoot().addResource("getInnovation").addMethod("GET", new LambdaIntegration(getInnovationFunction));
    }
}
