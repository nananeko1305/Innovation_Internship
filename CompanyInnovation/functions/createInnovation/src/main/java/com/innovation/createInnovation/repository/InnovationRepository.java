package com.innovation.createInnovation.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.innovation.createInnovation.entity.InnovationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InnovationRepository {

    private final DynamoDBMapper dynamoDBMapper;

    @Autowired
    public InnovationRepository(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public InnovationEntity submitInnovation(InnovationEntity innovationEntity){

        return null;
    }

}
