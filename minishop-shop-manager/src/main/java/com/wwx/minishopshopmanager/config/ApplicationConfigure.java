package com.wwx.minishopshopmanager.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationConfigure implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer() {

            //配置视图
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("shopAdmin/ShopAdmin");
            }
        };
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    /**
     *
     *                       资源解析
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /**
         *          解析本地路径下的图片
         */
        registry.addResourceHandler("/upload/**").addResourceLocations("file:D:/Project/MiniShop/image/upload/");
    }

    /**
     *       定制rabbitmq消息封装与解析
     *       默认为java序列化机制，这里改为json数据类型
     *
     * @return
     */
    /*@Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }*/
}
