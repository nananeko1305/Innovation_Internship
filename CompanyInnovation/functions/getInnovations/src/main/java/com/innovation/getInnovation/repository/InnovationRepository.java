package com.innovation.getInnovation.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
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

}
