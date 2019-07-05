package com.wwx.minishoppersonservice.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.wwx.minishop.utils.ConfigUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfig {

    /*将配置的druid加入到容器中*/
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druid(){
        return new DruidDataSource();
    }

    /*配置druid监控*/
    /*1.配置一个管理后台的servlet*/
    @Bean
    public ServletRegistrationBean statViewServlet(){
        return ConfigUtils.statViewServlet();
    }

    /*2.配置一个filter*/
    @Bean
    public FilterRegistrationBean webStatFilter(){
        return ConfigUtils.webStatFilter();
    }
}
