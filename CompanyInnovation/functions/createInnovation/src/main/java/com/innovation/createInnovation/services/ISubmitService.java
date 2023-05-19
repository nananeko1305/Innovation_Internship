package com.innovation.createInnovation.services;

import com.innovation.createInnovation.DTO.InnovationDTO;
import com.nimbusds.jwt.JWTClaimsSet;

public interface ISubmitService {
    InnovationDTO submitInnovation (InnovationDTO innovationDTO, JWTClaimsSet claimsSet);
}
