package com.innovation.createInnovation.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.innovation.createInnovation.entity.Innovation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class InnovationRepository {

//    private final DynamoDBMapper dynamoDBMapper;
//
//    @Autowired
//    public InnovationRepository(DynamoDBMapper dynamoDBMapper) {
//        this.dynamoDBMapper = dynamoDBMapper;
//    }
    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public void submitInnovation(Innovation innovationEntity){
        dynamoDBMapper.save(innovationEntity);
    }

}
