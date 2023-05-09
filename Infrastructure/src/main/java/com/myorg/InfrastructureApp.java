package com.myorg;

import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;

import java.util.Arrays;

public class InfrastructureApp {
    public static void main(final String[] args) {
        App app = new App();

        new InfrastructureStack(app, "InfrastructureStack", StackProps.builder()

                .env(Environment.builder()
                        .account("123456789012")
                        .region("eu-north-1")
                        .build())
               

                .build());
        new TestUDJStack(app, "TestUDJStack", StackProps.builder()
                
                .build());

        app.synth();
    }
}

