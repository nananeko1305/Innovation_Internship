package com.innovation.getInnovation.controller;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.serverless.proxy.spring.SpringBootProxyHandlerBuilder;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.innovation.getInnovation.GetInnovation;

import java.io.*;

public class LambdaHandler implements RequestStreamHandler {



    private static final SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;

    static {
        try {
            handler = new SpringBootProxyHandlerBuilder<AwsProxyRequest>()
                    .defaultProxy()
                    .asyncInit()
                    .springBootApplication(GetInnovation.class)
                    .buildAndInitialize();
        } catch (ContainerInitializationException e) {
            // if we fail here, we re-throw the exception to force another cold start
            e.printStackTrace();
            throw new RuntimeException("Could not initialize Spring Boot application", e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context)
            throws IOException {
        handler.proxyStream(inputStream, outputStream, context);

//        NaniExample(inputStream, outputStream, context);
    }

//    private String INNOVATIONS_TABLE = "Innovations"; //naziv tabele unutar DynamoDB
//    public void NaniExample(InputStream inputStream, OutputStream outputStream, Context context){
//
//        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream)); //citanje pistiglih podataka iz gateway
//        OutputStreamWriter writer = new OutputStreamWriter(outputStream); //pisanje novih podataka za gateaway
//        JSONParser parser = new JSONParser();      //helper za JSON
//        JSONObject responseObject = new JSONObject(); //kreiranje response objekta tipa K : V
//        JSONObject responseBody = new JSONObject(); //kreiranje response.body objekta tipa K : V
//
//
//        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient(); //definisanje klijenta za komunikaciju sa DynamoDB
//        DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(client); // povezivanje DynamoDB sa clientom
//
//        List<Innovation> innovationsByUser = null; //inicijalizacija liste
//
//        try {
//            JSONObject requestObject = (JSONObject) parser.parse(reader); // prosledjivanje URI-ja readeru na citanje
//
//            if(requestObject.get("pathParameters") != null) {
//
//                JSONObject pathParameters = (JSONObject) requestObject.get("pathParameters"); // uzimanje path parametra iz URI
//
//                if(pathParameters.get("userId") != null){
//
//                    Map<String, AttributeValue> expressionAttributeValues = new HashMap<>(); //kreiranje filtera po kome ce se vrstiti pretraga
//                    expressionAttributeValues.put(":value", new AttributeValue().withS((String) pathParameters.get("userId"))); //dodavanje atributa za pretragu i ubacivanje
//                    //iz pathParametra
//                    DynamoDBQueryExpression<Innovation> queryExpression = new DynamoDBQueryExpression<Innovation>()
//                            .withKeyConditionExpression("userId = :value")  //kreiranje query-ja koji ce se izvrsiti
//                            .withExpressionAttributeValues(expressionAttributeValues);
//
//                    innovationsByUser = dynamoDBMapper.query(Innovation.class, queryExpression);
//                }
//            }
//
//            if (innovationsByUser != null) {
//                responseBody.put("innovations", innovationsByUser); //ubacivanje liste nazad
//                responseObject.put("statusCode", 200);
//            }else {
//                responseBody.put("message", "No Items found"); //error
//                responseObject.put("statusCode", 404);
//            }
//            responseObject.put("body", responseBody.toJSONString());
//
//        } catch (ParseException e) {
//            context.getLogger().log("ERROR : "+e.getMessage()); //logovanje errora
//        }
//
//        writer.write(responseObject.toString()); //vracanje responsa na API Gateway
//        reader.close();
//        writer.close();
//    }
}
