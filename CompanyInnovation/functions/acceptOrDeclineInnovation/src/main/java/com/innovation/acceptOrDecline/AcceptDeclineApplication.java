package com.innovation.acceptOrDecline;

import com.innovation.acceptOrDecline.entity.Innovation;
import com.innovation.acceptOrDecline.repository.InnovationRepository;
import com.innovation.acceptOrDecline.services.MailService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

@SpringBootApplication
public class AcceptDeclineApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcceptDeclineApplication.class, args);
	}



	@Bean
	MailSender mailSender(){return new MailSender() {
		@Override
		public void send(SimpleMailMessage simpleMessage) throws MailException {

		}

		@Override
		public void send(SimpleMailMessage... simpleMessages) throws MailException {

		}
	};}


}
