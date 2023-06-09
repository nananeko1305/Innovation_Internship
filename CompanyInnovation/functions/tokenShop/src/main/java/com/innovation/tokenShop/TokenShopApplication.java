package com.innovation.tokenShop;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TokenShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(TokenShopApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){ return new ModelMapper();}
}
