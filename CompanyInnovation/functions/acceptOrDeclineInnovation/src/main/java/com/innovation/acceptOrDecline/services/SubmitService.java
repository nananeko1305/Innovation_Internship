package com.innovation.acceptOrDecline.services;

import com.innovation.acceptOrDecline.config.CognitoUserDataRetriever;
import com.innovation.acceptOrDecline.config.TokenUtils;
import com.innovation.acceptOrDecline.dto.InnovationDTO;
import com.innovation.acceptOrDecline.entity.Innovation;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service

public class SubmitService implements ISubmitService {

    private final TokenUtils tokenUtils;
    private final MailService mailService;
    private final IUserTokenService userTokenService;

    private final CognitoUserDataRetriever cognito;


    public SubmitService(IUserTokenService userTokenService, MailService mailService, CognitoUserDataRetriever cognito, TokenUtils tokenUtils) {
        this.userTokenService = userTokenService;
        this.mailService= mailService;
        this.cognito = cognito;
        this.tokenUtils = tokenUtils;
    }


    @Override
    public InnovationDTO submitComment(InnovationDTO innovationDTO, JWTClaimsSet claimsSet) {
        String userEmail =cognito.getUser(innovationDTO.getUsername());
        Innovation innovation = new Innovation(innovationDTO);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(tokenUtils.getEmailFromToken(claimsSet));
        message.setTo(userEmail);
        message.setSubject("Status update from "+ innovation.getUsername());
        if(innovation.getStatus().toString().equals("DECLINED")) {
            message.setText("Innovation status has changed to: " + innovation.getStatus() + "\n\n" + innovation.getComment());
            mailService.sendMessage(message);
        }
        else {
            message.setText("Innovation status has changed to: " + innovation.getStatus());
            mailService.sendMessage(message);
            userTokenService.addTokens(innovation.getUserId());
        }

        return innovationDTO;
    }
}
