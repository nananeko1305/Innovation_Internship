package com.innovation.getInnovation.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.innovation.getInnovation.config.DynamoConfig;
import com.innovation.getInnovation.domain.model.Innovation;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InnovationRepository{

    private final DynamoConfig dynamoConfig;

    public InnovationRepository(DynamoConfig dynamoConfig) {
        this.dynamoConfig = dynamoConfig;
    }

    public List<Innovation> GetAll(){
        return dynamoConfig.dynamoDBMapper().scan(Innovation.class, new DynamoDBScanExpression());
    }

    public List<Innovation> GetInnovationsForUser(String userId) {

        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":userId", new AttributeValue().withS(userId));

        Innovation innovation = new Innovation();
        innovation.setUserId(userId);
        DynamoDBQueryExpression<Innovation> queryExpression = new DynamoDBQueryExpression<Innovation>()
                .withIndexName("userIdIndex")
                .withKeyConditionExpression("userId = :userId")
                .withExpressionAttributeValues(expressionAttributeValues)
                .withConsistentRead(false);

        PaginatedQueryList<Innovation> result = dynamoConfig.dynamoDBMapper().query(Innovation.class, queryExpression);
        return result.subList(0, result.size());
    }

}
