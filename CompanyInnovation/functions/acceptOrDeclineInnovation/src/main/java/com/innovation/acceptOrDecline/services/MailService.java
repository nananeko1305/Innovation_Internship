package com.innovation.acceptOrDecline.services;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailService implements IMailService {
    private MailSender mailSender;

    public MailService(MailSender mailSender) {
        this.mailSender = mailSender;
    }
    public void sendMessage(SimpleMailMessage simpleMailMessage) {
        mailSender.send(simpleMailMessage);
    }
}
