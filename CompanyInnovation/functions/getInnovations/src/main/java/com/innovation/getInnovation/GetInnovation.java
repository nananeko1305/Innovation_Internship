package com.innovation.getInnovation;

import com.innovation.getInnovation.config.CorsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class GetInnovation {

    public static void main(String[] args) {
        SpringApplication.run(GetInnovation.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        System.out.println("CORS STARTTED");
        return new CorsConfig();
    }

}
