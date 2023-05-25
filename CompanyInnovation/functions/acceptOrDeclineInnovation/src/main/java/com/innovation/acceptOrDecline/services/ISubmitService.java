package com.innovation.acceptOrDecline.services;


import com.innovation.acceptOrDecline.dto.InnovationDTO;
import com.nimbusds.jwt.JWTClaimsSet;

public interface ISubmitService {
    InnovationDTO submitComment(InnovationDTO innovationDTO, JWTClaimsSet claimsSet);
}
