package com.innovation.getInnovation;

import com.innovation.common.config.CorsConfig;
import com.innovation.common.config.DynamoConfig;
import com.innovation.common.config.TokenUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GetInnovation {

    public static void main(String[] args) {
        SpringApplication.run(GetInnovation.class, args);
    }

    @Bean
    public CorsConfig corsConfig() {
        return new CorsConfig();
    }

    @Bean
    public DynamoConfig dynamoConfig(){
        return new DynamoConfig();
    }

    @Bean
    public TokenUtils tokenUtils(){
        return new TokenUtils();
    }

}
