package com.innovation.getInnovation.domain.model;
import com.innovation.getInnovation.domain.dto.InnovationDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Innovation {

    private int id;
    private String userId;
    private String username;
    private String fullName;
    private String title;
    private String description;
    private String comment;
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