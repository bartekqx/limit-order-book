package com.bartek.sulima.soft.application.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper(JavaTimeModule dateTimeModule) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(dateTimeModule);
        return objectMapper;
    }

    @Bean
    public JavaTimeModule dateTimeModule(){
        return new JavaTimeModule();
    }

}
