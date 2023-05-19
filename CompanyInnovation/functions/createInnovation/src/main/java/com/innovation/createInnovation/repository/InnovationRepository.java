package com.innovation.createInnovation.repository;

import com.innovation.createInnovation.config.DynamoConfig;
import com.innovation.createInnovation.entity.Innovation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class InnovationRepository {

    @Autowired
    private DynamoConfig dynamoConfig;

    public void submitInnovation(Innovation innovationEntity){
        dynamoConfig.dynamoDBMapper().save(innovationEntity);
    }


}
