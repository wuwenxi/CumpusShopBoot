package com.wwx.minishopprovider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.jms.annotation.EnableJms;

@EnableJms
@MapperScan("com.wwx.minishopprovider.dao")
@EnableCaching
@EnableDiscoveryClient
@SpringBootApplication
public class MinishopProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinishopProviderApplication.class, args);
    }

}
