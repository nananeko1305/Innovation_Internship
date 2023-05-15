package com.innovation.getInnovation.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.innovation.getInnovation.domain.model.Innovation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InnovationRepository{

    private final DynamoDBMapper dynamoDBMapper;

    @Autowired
    public InnovationRepository(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public List<Innovation> GetAllInnovations(String userId, String getType){

        System.out.println("UserId: " + userId);
        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":val", new AttributeValue().withS(userId));

        DynamoDBQueryExpression<Innovation> queryExpression = new DynamoDBQueryExpression<Innovation>()
                .withKeyConditionExpression("userId = :val")
                .withExpressionAttributeValues(expressionAttributeValues);

        System.out.println(queryExpression.getExpressionAttributeValues());

        return dynamoDBMapper.query(Innovation.class, queryExpression);
    }

}
