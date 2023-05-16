package com.innovation.createInnovation.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoConfig {

    @Value("${amazon.aws.dynamodb.endpoint}")
    private String dynamoDbEndpoint;
    @Value("${amazon.aws.accesskey}")
    private String awsAccessKey;
    @Value("${amazon.aws.secretkey}")
    private String awsSecretKey;
    @Value("${amazon.aws.sessiontoken}")
    private String awsSessionToken;

    @Bean
    public DynamoDBMapper dynamoDBMapper() {
        return new DynamoDBMapper(amazonDynamoDB());
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
//        System.out.println(dynamoDbEndpoint + " " + awsSecretKey + " " + awsAccessKey + " " + awsSessionToken);
        return AmazonDynamoDBClientBuilder
                .standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(dynamoDbEndpoint , "eu-north-1"))
                .withCredentials(awsCredentialsProvider())
                .build();
    }

    private AWSCredentialsProvider awsCredentialsProvider(){
        BasicSessionCredentials awsCreds = new BasicSessionCredentials(awsAccessKey, awsSecretKey, awsSessionToken);
        return new AWSStaticCredentialsProvider(awsCreds);
    }



}
