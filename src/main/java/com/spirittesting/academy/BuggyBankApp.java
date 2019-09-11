package com.spirittesting.academy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BuggyBankApp {

    public static void main(String[] args) {
        SpringApplication.run(BuggyBankApp.class, args);
    }

}
