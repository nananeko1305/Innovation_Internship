package com.innovation.acceptOrDecline.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data


public class InnovationEntity {

    private Integer id;
    private String userId;
    private String username;
    private String fullName;
    private String title;
    private String description;
    private String comment;
    private Status status;


}
