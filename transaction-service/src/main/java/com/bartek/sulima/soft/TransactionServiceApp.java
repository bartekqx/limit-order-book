package com.bartek.sulima.soft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class TransactionServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(TransactionServiceApp.class, args);
    }
}
