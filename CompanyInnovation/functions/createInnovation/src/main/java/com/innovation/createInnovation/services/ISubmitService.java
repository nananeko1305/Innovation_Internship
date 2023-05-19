package com.innovation.createInnovation.services;


import com.innovation.createInnovation.domain.dto.InnovationDTO;

public interface ISubmitService {
    InnovationDTO submitInnovation (InnovationDTO innovationDTO);
}
