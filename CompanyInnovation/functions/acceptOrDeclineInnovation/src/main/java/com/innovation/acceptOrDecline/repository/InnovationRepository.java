package com.innovation.acceptOrDecline.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.innovation.acceptOrDecline.entity.Innovation;
import org.springframework.stereotype.Repository;

@Repository
public class InnovationRepository {

    private final DynamoDBMapper dynamoDBMapper;

    public InnovationRepository(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public void save(Innovation innovation){
        dynamoDBMapper.save(innovation);
    }
}

