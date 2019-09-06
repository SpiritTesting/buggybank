package com.spirittesting.academy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KundennummerConfig {

    @Bean
    public int initialeKundennummer() {
        return 1;
    }

    @Bean
    public String kundennummerPattern() {
        return "BBKD%04d";
    }

}
