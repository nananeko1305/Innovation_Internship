package com.innovation.createInnovation;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CreateInnovationApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreateInnovationApplication.class, args);
	}

		@Bean
		public ModelMapper modelMapper(){ return new ModelMapper();}


}
