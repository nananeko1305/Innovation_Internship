package com.innovation.acceptOrDecline.services;


import com.innovation.acceptOrDecline.dto.InnovationDTO;
import com.innovation.acceptOrDecline.entity.InnovationEntity;
import com.innovation.acceptOrDecline.entity.Status;
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
    public InnovationDTO submitComment(InnovationDTO innovationDTO) {
        InnovationEntity innovationEntity = mapper.map(innovationDTO, InnovationEntity.class);
        //ovde treba da se ubaci u bazu

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("innovation.lead@outlook.com");
        message.setTo("innovation.employee@outlook.com");
        message.setSubject("Status update from "+innovationEntity.getFullName());
        message.setText("");
        message.setText(innovationEntity.getComment());
        mailService.sendMessage(message);
        //dodati i za komentar
        //dodela poena???


        return mapper.map(innovationEntity, InnovationDTO.class);    }


}
