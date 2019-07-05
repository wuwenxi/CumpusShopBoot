package com.wwx.minishopfrontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/frontend")
public class FrontendManageController {

    //跳转商铺页
    @GetMapping("/shopList")
    public String shopList(){
        return "frontend/shopList";
    }

    //跳转美食饮品类别页
    @GetMapping("/food")
    public String food(){ return "frontend/food";}

    //跳转二手市场类别页
    @GetMapping("/second_market")
    public String secondMarket(){ return "frontend/second_market"; }

    //跳转租赁市场类别页
    @GetMapping("/rent_market")
    public String rentMarket(){ return "frontend/rent_market"; }

    //跳转休闲娱乐类别页
    @GetMapping("/entertainment")
    public String entertainment(){ return "frontend/entertainment"; }

    //跳转美容美发类别页
    @GetMapping("/hairdressing")
    public String hairdressing(){ return "frontend/hairdressing"; }

    //跳转培训教育类别页
    @GetMapping("/education")
    public String education(){ return "frontend/education"; }

    //跳转运动健身类别页
    @GetMapping("/exercise")
    public String exercise(){ return "frontend/exercise"; }

    //跳转其他类别页
    @GetMapping("/other")
    public String other(){ return "frontend/other"; }

    @GetMapping("/shopDetail")
    public String shopDetail(){ return "frontend/shopDetail"; }

    @GetMapping("/productDetail")
    public String productDetail(){ return "frontend/productDetail"; }



}
