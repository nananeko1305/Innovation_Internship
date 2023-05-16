package com.innovation.acceptOrDecline.services;


import com.innovation.acceptOrDecline.dto.InnovationDTO;
import com.innovation.acceptOrDecline.entity.Innovation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service

public class SubmitService implements ISubmitService {

    @Autowired
    private MailService mailService;

    @Override
    public InnovationDTO submitComment(InnovationDTO innovationDTO) {
        Innovation innovation = new Innovation(innovationDTO);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("innovation.lead@outlook.com");
        message.setTo("innovation.employee@outlook.com");
        message.setSubject("Status update from "+ innovation.getFullName());
        if(innovation.getStatus().toString().equals("DECLINED")) {
            message.setText("Innovation status has changed to: " + innovation.getStatus() + "\n\n" + innovation.getComment());
        }
        else
            message.setText("Innovation status has changed to: "+ innovation.getStatus());
        mailService.sendMessage(message);


        //dodela poena???


        return innovationDTO;
    }
}
