package com.innovation.getInnovation.service;

import com.innovation.common.domain.dto.InnovationDTO;
import com.innovation.common.domain.model.Innovation;
import com.nimbusds.jwt.JWTClaimsSet;

import java.util.List;


public interface InnovationService {

    List<Innovation> GetAll(JWTClaimsSet claimsSet);

    List<InnovationDTO> convertToDtoList(List<Innovation> innovationList);

}
