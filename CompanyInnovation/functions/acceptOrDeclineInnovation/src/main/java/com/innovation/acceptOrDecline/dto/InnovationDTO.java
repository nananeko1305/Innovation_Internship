package com.innovation.acceptOrDecline.dto;

import com.innovation.acceptOrDecline.entity.Status;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InnovationDTO {

    private String id;
    private String userId;
    private String username;
    private String fullName;
    private String title;
    private String description;
    private String comment;
    private Status status;

}