package com.wwx.minishopfrontend.controller;

import com.wwx.minishop.entity.Shop;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/frontend")
public class SearchController {

    private final RestTemplate restTemplate;

    @Autowired
    public SearchController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/searchWithName")
    public List<Shop> searchWithName(@Param("name")String name){
        if(name!=null&& !name.equals("")){
            return restTemplate.getForObject("http://MINISHOP-ELASTICSEARCH/es/findShopWithName?name="+name,List.class);
        }
        return null;
    }
}
