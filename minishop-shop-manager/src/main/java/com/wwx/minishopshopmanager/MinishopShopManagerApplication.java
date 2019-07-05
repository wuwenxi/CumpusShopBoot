package com.wwx.minishopshopmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.jms.annotation.EnableJms;

@EnableJms
@EnableDiscoveryClient
@SpringBootApplication
public class MinishopShopManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinishopShopManagerApplication.class, args);
    }

}
