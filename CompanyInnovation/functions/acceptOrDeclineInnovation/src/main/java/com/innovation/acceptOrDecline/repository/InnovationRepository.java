package com.innovation.acceptOrDecline.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.innovation.acceptOrDecline.entity.Innovation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class InnovationRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;
    public void save(Innovation innovation){
        dynamoDBMapper.save(innovation);
    };
}

