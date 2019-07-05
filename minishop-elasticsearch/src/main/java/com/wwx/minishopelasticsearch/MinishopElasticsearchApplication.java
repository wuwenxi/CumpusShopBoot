package com.wwx.minishopelasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.jms.annotation.EnableJms;

@EnableDiscoveryClient
@EnableJms
@SpringBootApplication
public class MinishopElasticsearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinishopElasticsearchApplication.class, args);
    }

}
