package com.innovation.acceptOrDecline;

import com.innovation.acceptOrDecline.entity.Innovation;
import com.innovation.acceptOrDecline.repository.InnovationRepository;
import com.innovation.acceptOrDecline.repository.UserTokensRepository;
import com.innovation.acceptOrDecline.services.UserTokenService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class AcceptDeclineApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcceptDeclineApplication.class, args);
	}




}
