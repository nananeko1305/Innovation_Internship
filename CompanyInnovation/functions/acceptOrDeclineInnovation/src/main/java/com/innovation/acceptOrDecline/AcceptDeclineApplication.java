package com.innovation.acceptOrDecline;

import com.innovation.acceptOrDecline.entity.InnovationEntity;
import com.innovation.acceptOrDecline.repository.InnovationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AcceptDeclineApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcceptDeclineApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){ return new ModelMapper();}

	@Bean
	public InnovationRepository innovationRepository(){return new InnovationRepository() {
		@Override
		public InnovationEntity findById(Integer id) {
			return null;
		}

		@Override
		public void save(InnovationEntity entity) {

		}
	}; }


}
