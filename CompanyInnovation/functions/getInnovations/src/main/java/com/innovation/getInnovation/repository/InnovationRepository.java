package com.innovation.getInnovation.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.innovation.getInnovation.domain.model.Innovation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InnovationRepository{

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public List<Innovation> GetAll(){
        return dynamoDBMapper.scan(Innovation.class, new DynamoDBScanExpression());
    }

    public List<Innovation> GetInnovationsForUser(String userId) {

        Innovation innovation = new Innovation();
        innovation.setUserId(userId);
        DynamoDBQueryExpression<Innovation> queryExpression = new DynamoDBQueryExpression<Innovation>()
                .withIndexName("userId")
                .withConsistentRead(false)
                .withHashKeyValues(innovation);

        PaginatedQueryList<Innovation> result = dynamoDBMapper.query(Innovation.class, queryExpression);
        return result.subList(0, result.size());
    }

}
