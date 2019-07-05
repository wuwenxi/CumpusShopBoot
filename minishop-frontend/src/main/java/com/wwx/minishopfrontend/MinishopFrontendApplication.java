package com.wwx.minishopfrontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MinishopFrontendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinishopFrontendApplication.class, args);
    }

}
