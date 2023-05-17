package com.innovation.manageShop;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ManageShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManageShopApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){ return new ModelMapper();}



}
