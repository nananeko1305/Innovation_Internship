package com.innovation.acceptOrDecline.services;

import com.innovation.acceptOrDecline.config.TokenUtils;
import com.innovation.acceptOrDecline.dto.InnovationDTO;
import com.innovation.acceptOrDecline.entity.Innovation;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service

public class SubmitService implements ISubmitService {

    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private MailService mailService;
    @Autowired
    private IUserTokenService userTokenService;


    public SubmitService(IUserTokenService userTokenService, MailService mailService) {
        this.userTokenService = userTokenService;
        this.mailService= mailService;
    }


    @Override
    public InnovationDTO submitComment(InnovationDTO innovationDTO, JWTClaimsSet claimsSet) {
        Innovation innovation = new Innovation(innovationDTO);
        SimpleMailMessage message = new SimpleMailMessage();
        String email= tokenUtils.getEmailFromToken(claimsSet);
        message.setFrom(tokenUtils.getEmailFromToken(claimsSet));
        message.setTo("innovation.employee@outlook.com");
        message.setSubject("Status update from "+ innovation.getUsername());
        if(innovation.getStatus().toString().equals("DECLINED")) {
            message.setText("Innovation status has changed to: " + innovation.getStatus() + "\n\n" + innovation.getComment());
        }
        else
            message.setText("Innovation status has changed to: "+ innovation.getStatus());
        mailService.sendMessage(message);

        System.out.println(innovation.toString());

        userTokenService.addTokens(innovation.getUserId());


        return innovationDTO;
    }
}
