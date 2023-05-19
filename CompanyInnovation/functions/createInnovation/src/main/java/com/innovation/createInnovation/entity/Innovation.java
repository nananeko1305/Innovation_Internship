package com.innovation.createInnovation.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@DynamoDBTable(tableName = "innovations")
public class Innovation {

    @DynamoDBHashKey(attributeName = "id")
    @DynamoDBAutoGeneratedKey
    private String id;

    @DynamoDBAttribute
    private String userId;

    @DynamoDBAttribute
    private String username;

    @DynamoDBAttribute
    private String fullName;

    @DynamoDBAttribute
    private String title;

    @DynamoDBAttribute
    private String description;

    @DynamoDBAttribute
    private String comment;

    @DynamoDBAttribute
    @DynamoDBTypeConvertedEnum
    private Status status;


}
