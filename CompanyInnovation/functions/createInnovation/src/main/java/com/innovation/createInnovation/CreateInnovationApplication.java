package com.innovation.createInnovation;

import com.innovation.common.config.CorsConfig;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class CreateInnovationApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreateInnovationApplication.class, args);
	}

		@Bean
		public ModelMapper modelMapper(){ return new ModelMapper();}

		@Bean
		public WebMvcConfigurer corsConfigurer() {
			return new CorsConfig();
		}

}
