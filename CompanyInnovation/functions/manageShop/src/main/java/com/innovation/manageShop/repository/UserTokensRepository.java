package com.innovation.manageShop.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.innovation.manageShop.entity.ProductEntity;
import com.innovation.manageShop.entity.UserTokenEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserTokensRepository{
    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public void saveUser (UserTokenEntity userToken){dynamoDBMapper.save(userToken);}
    public UserTokenEntity findUser (String userId){
        return dynamoDBMapper.load(UserTokenEntity.class, userId);
    }

}
