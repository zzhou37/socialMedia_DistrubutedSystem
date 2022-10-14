package com.network.userauthorizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(
        basePackages = "com.network.clients"
)
public class UserAuthorizerApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserAuthorizerApplication.class, args);
    }
}
