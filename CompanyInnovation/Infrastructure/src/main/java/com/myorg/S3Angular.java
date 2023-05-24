package com.myorg;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.s3.BlockPublicAccess;
import software.amazon.awscdk.services.s3.Bucket;
import software.amazon.awscdk.services.s3.BucketAccessControl;
import software.amazon.awscdk.services.s3.deployment.BucketDeployment;
import software.amazon.awscdk.services.s3.deployment.ISource;
import software.amazon.awscdk.services.s3.deployment.Source;
import software.constructs.Construct;

import java.util.Collections;
import java.util.List;

public class S3Angular extends Stack {

    String angularPath = "../Front-End/dist/frontend";

//    String angularPath = "";

    ISource angularSource = Source.asset(angularPath);
    List<ISource> sources = Collections.singletonList(angularSource);

    public S3Angular(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public S3Angular(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        Bucket s3BucketAngular = Bucket.Builder
                .create(this, "S3BucketAngular")
                .bucketName("s3-bucket-angular")
                .websiteIndexDocument("index.html")
                .websiteErrorDocument("index.html")
                .blockPublicAccess(BlockPublicAccess.BLOCK_ACLS)
                .accessControl(BucketAccessControl.BUCKET_OWNER_FULL_CONTROL)
                .publicReadAccess(true)
                .build();

        BucketDeployment.Builder.create(this, "S3BucketDeployment")
                .sources(sources)
                .destinationBucket(s3BucketAngular)
                .build();

    }

}
