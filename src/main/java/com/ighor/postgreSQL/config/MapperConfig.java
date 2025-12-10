package com.ighor.postgreSQL.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        //Here we have access to model mapper in our entire app
        return new ModelMapper();
    }
}
