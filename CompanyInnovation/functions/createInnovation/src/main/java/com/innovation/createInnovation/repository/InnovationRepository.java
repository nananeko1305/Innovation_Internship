package com.innovation.createInnovation.repository;

import com.innovation.createInnovation.config.DynamoConfig;
import com.innovation.createInnovation.entity.Innovation;
import org.springframework.stereotype.Repository;

@Repository
public class InnovationRepository {

    private final DynamoConfig dynamoConfig;

    public InnovationRepository(DynamoConfig dynamoConfig) {
        this.dynamoConfig = dynamoConfig;
    }

    public void submitInnovation(Innovation innovationEntity){
        dynamoConfig.dynamoDBMapper().save(innovationEntity);
    }


}
