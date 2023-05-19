package com.innovation.createInnovation.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.innovation.createInnovation.domain.entity.Innovation;
import org.springframework.stereotype.Repository;


@Repository
public class InnovationRepository {
    private DynamoDBMapper dynamoDBMapper;

    public void submitInnovation(Innovation innovationEntity){
        dynamoDBMapper.save(innovationEntity);
    }


}
