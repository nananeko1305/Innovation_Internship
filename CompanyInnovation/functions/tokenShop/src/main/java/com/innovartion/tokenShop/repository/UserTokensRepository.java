package com.innovartion.tokenShop.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.innovartion.tokenShop.entity.UserTokenEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserTokensRepository{
    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public void saveUser (UserTokenEntity userToken){dynamoDBMapper.save(userToken);}
    public UserTokenEntity findUser (String userId){
        return dynamoDBMapper.load(UserTokenEntity.class, userId);
    }

}
