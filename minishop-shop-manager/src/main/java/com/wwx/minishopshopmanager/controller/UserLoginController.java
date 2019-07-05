package com.wwx.minishopshopmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserLoginController {

    @GetMapping("/shopManagerLogin")
    public String shopManagerLogin(){
        return "login/shopManagerLogin";
    }
}
