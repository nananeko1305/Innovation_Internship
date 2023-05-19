package com.innovation.common.domain.dto;

import com.innovation.common.domain.model.Innovation;
import com.innovation.common.domain.model.Status;
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


    public InnovationDTO(Innovation innovation){
        this.id = innovation.getId();
        this.userId = innovation.getUserId();
        this.username = innovation.getUsername();
        this.fullName = innovation.getFullName();
        this.title = innovation.getTitle();
        this.description = innovation.getDescription();
        this.comment = innovation.getComment();
        this.status = innovation.getStatus();
    }

}