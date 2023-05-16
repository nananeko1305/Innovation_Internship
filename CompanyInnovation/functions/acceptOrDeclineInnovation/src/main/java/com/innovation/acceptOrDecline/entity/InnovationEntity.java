package com.innovation.acceptOrDecline.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data


public class InnovationEntity {

    private String id;
    private String userId;
    private String username;
    private String fullName;
    private String title;
    private String description;
    private String comment;
    private Status status;


}
