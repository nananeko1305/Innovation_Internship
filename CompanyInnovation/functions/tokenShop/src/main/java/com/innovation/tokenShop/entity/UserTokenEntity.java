package com.innovation.tokenShop.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@DynamoDBTable(tableName = "usersTokens")
public class UserTokenEntity {
    @DynamoDBHashKey(attributeName = "userId")
    private String userId;
    @DynamoDBAttribute
    private int tokens;
}
