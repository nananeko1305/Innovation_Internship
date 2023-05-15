package com.innovation.acceptOrDecline.services;

import org.springframework.mail.SimpleMailMessage;

public interface IMailService {

    void sendMessage (SimpleMailMessage simpleMailMessage);
}
