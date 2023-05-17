package com.innovation.acceptOrDecline.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.innovation.acceptOrDecline.dto.InnovationDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "innovations1")
public class Innovation {

    @DynamoDBHashKey(attributeName = "id")
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


    public Innovation(InnovationDTO innovationDTO){
        this.id = innovationDTO.getId();
        this.userId = innovationDTO.getUserId();
        this.username = innovationDTO.getUsername();
        this.fullName = innovationDTO.getFullName();
        this.title = innovationDTO.getTitle();
        this.description = innovationDTO.getDescription();
        this.comment = innovationDTO.getComment();
        this.status = innovationDTO.getStatus();
    }

}