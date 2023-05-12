package com.innovation.createInnovation.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.innovation.createInnovation.entity.Status;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InnovationDTO {

    private Integer id;
    private String userId;
    private String username;
    private String fullName;
    private String title;
    private String description;
    private String comment;
    private Status status;
}
