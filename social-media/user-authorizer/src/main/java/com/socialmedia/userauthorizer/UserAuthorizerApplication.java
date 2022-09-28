package com.socialmedia.userauthorizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class UserAuthorizerApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserAuthorizerApplication.class, args);
    }
}
