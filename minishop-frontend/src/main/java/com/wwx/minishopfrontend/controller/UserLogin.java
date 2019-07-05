package com.wwx.minishopfrontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class UserLogin {

    @RequestMapping("/userLogin")
    public String userLogin(){
        return "login/userLogin";
    }

    @GetMapping("/register")
    public String userRegister(){
        return "login/userRegister";
    }

    @RequestMapping("/registerShop")
    public String registerShop(){
        return "registerShopAdmin";
    }

    @GetMapping("/order")
    public String order(){ return "frontend/order"; }

}

