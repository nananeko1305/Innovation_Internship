package com.innovation.acceptOrDecline.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.innovation.acceptOrDecline.entity.UserToken;
import org.springframework.stereotype.Repository;

@Repository
public class UserTokensRepository{

    private DynamoDBMapper dynamoDBMapper;

    public void saveUser (UserToken userToken){dynamoDBMapper.save(userToken);}
    public UserToken findUser (String userId){
        return dynamoDBMapper.load(UserToken.class, userId);
    }





}
