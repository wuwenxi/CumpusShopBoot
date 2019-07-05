package com.wwx.minishopusercollection;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@MapperScan("com.wwx.minishopusercollection.dao")
@EnableDiscoveryClient
@SpringBootApplication
public class MinishopUserCollectionApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinishopUserCollectionApplication.class, args);
    }

}
