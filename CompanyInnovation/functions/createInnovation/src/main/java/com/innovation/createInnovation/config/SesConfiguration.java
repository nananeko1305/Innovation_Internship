package com.innovation.createInnovation.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import io.awspring.cloud.ses.SimpleEmailServiceMailSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;

@Configuration
public class SesConfiguration {

    @Bean
    public MailSender mailSender (AmazonSimpleEmailService amazonSimpleEmailService){
        return new SimpleEmailServiceMailSender(amazonSimpleEmailService);
    }

    @Bean
    public  AmazonSimpleEmailService amazonSimpleEmailService(){

        return AmazonSimpleEmailServiceClientBuilder
                .standard()
                .build();
    }
}
