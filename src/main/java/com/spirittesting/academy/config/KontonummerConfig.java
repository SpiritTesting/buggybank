package com.spirittesting.academy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KontonummerConfig {

    @Bean
    public int initialeKontonummer() {
        return 1;
    }

    @Bean
    public String kontonummerPattern() {
        return "1234%04d";
    }

}
