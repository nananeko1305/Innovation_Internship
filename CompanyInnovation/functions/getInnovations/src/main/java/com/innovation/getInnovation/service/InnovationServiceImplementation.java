package com.innovation.getInnovation.service;

import com.innovation.common.config.TokenUtils;
import com.innovation.common.domain.dto.InnovationDTO;
import com.innovation.common.domain.model.Innovation;
import com.innovation.getInnovation.repository.InnovationRepository;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InnovationServiceImplementation implements InnovationService {

    @Autowired
    private InnovationRepository innovationRepository;

    @Override
    public List<Innovation> GetAll(JWTClaimsSet claimsSet) {

        if (TokenUtils.getRoleFromToken(claimsSet).equals("Employee")){
            return innovationRepository.GetInnovationsForUser(TokenUtils.getIdFromToken(claimsSet));

        }else if(TokenUtils.getRoleFromToken(claimsSet).equals("Lead")) {
            return innovationRepository.GetAll();

        }
        return null;
    }

    public List<InnovationDTO> convertToDtoList(List<Innovation> innovationList) {
        List<InnovationDTO> innovationDtoList = new ArrayList<>();
        for (Innovation innovation : innovationList) {
            InnovationDTO innovationDto = new InnovationDTO(innovation);
            innovationDtoList.add(innovationDto);
        }
        return innovationDtoList;
    }

}
