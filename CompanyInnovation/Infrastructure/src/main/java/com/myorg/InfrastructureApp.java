package com.myorg;

import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;

public class InfrastructureApp {
    public static void main(final String[] args) {
        App app = new App();

        new InfrastructureStack(app, "InfrastructureStack", StackProps.builder()
                .env(Environment.builder()
                        .account("696993701802")
                        .region("eu-north-1")
                        .build())

                .build());
        new S3Angular(app, "S3Angular", StackProps.builder().build());


        app.synth();

    }
}

