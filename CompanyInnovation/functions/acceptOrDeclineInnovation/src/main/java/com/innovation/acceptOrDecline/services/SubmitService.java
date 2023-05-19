package com.innovation.acceptOrDecline.services;


import com.innovation.acceptOrDecline.dto.InnovationDTO;
import com.innovation.acceptOrDecline.entity.Innovation;
import com.innovation.acceptOrDecline.entity.UserToken;
import com.innovation.getInnovation.config.TokenUtils;
import com.nimbusds.jwt.JWTClaimsSet;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service

public class SubmitService implements ISubmitService {

    private MailService mailService;
    private IUserTokenService userTokenService;


    public SubmitService(IUserTokenService userTokenService, MailService mailService) {
        this.userTokenService = userTokenService;
        this.mailService= mailService;
    }


    @Override
    public InnovationDTO submitComment(InnovationDTO innovationDTO, JWTClaimsSet claimsSet) {
        Innovation innovation = new Innovation(innovationDTO);
        SimpleMailMessage message = new SimpleMailMessage();
        String email= TokenUtils.getEmailFromToken(claimsSet);
        message.setFrom(TokenUtils.getEmailFromToken(claimsSet));
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
