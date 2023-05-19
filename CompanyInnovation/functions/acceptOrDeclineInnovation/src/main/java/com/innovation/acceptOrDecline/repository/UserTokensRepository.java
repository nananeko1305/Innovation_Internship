package com.innovation.acceptOrDecline.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.innovation.acceptOrDecline.entity.Innovation;
import com.innovation.acceptOrDecline.entity.UserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserTokensRepository{
    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public void saveUser (UserToken userToken){dynamoDBMapper.save(userToken);}
    public UserToken findUser (String userId){
        return dynamoDBMapper.load(UserToken.class, userId);
    }





}
