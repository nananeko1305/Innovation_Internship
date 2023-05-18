package com.innovation.acceptOrDecline.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.innovation.acceptOrDecline.entity.Innovation;
import com.innovation.acceptOrDecline.entity.UserToken;
import org.springframework.beans.factory.annotation.Autowired;

public class UserTokensRepository {
    @Autowired
    private DynamoDBMapper dynamoDBMapper;
    public void save(UserToken userToken){
        dynamoDBMapper.save(userToken);
    };
}
