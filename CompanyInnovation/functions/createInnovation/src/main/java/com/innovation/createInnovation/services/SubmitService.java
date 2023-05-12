package com.innovation.createInnovation.services;

import com.innovation.createInnovation.DTO.InnovationDTO;
import com.innovation.createInnovation.entity.InnovationEntity;
import org.modelmapper.ModelMapper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service

public class SubmitService implements ISubmitService {

    private final ModelMapper mapper;
    private final IMailService mailService;

    public SubmitService(ModelMapper mapper, IMailService mailService) {
        this.mapper = mapper;
        this.mailService = mailService;
    }



    @Override
    public InnovationDTO submitInnovation(InnovationDTO innovationDTO) {
        InnovationEntity innovationEntity = mapper.map(innovationDTO, InnovationEntity.class);
        //ovde treba da se ubaci u bazu

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("compani.innovation.dept@outlook.com");
        message.setTo("innovation.lead@outlook.com");
        message.setSubject("New innovation by "+innovationEntity.getFullName());
        message.setText(innovationEntity.getDescription());
        mailService.sendMessage(message);


        return mapper.map(innovationEntity, InnovationDTO.class);    }
}
