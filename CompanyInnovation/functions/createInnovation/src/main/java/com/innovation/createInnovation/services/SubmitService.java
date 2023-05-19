package com.innovation.createInnovation.services;

import com.innovation.createInnovation.DTO.InnovationDTO;
import com.innovation.createInnovation.entity.Innovation;
import com.innovation.createInnovation.repository.InnovationRepository;
import com.nimbusds.jwt.JWTClaimsSet;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service

public class SubmitService implements ISubmitService {

    private final ModelMapper mapper;
    private final IMailService mailService;

    @Autowired
    private InnovationRepository innovationRepository;

    public SubmitService(ModelMapper mapper, IMailService mailService) {
        this.mapper = mapper;
        this.mailService = mailService;
    }



    @Override
    public InnovationDTO submitInnovation(InnovationDTO innovationDTO, JWTClaimsSet claimsSet) {
        Innovation innovationEntity = mapper.map(innovationDTO, Innovation.class);
        innovationEntity.setId(null);
        innovationRepository.submitInnovation(innovationEntity); //upis u bazu

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("compani.innovation.dept@outlook.com");
        message.setTo("innovation.lead@outlook.com");
        message.setSubject("New innovation by "+innovationEntity.getFullName());
        message.setText(innovationEntity.getDescription());
//        mailService.sendMessage(message);


        return mapper.map(innovationEntity, InnovationDTO.class);    }
}
