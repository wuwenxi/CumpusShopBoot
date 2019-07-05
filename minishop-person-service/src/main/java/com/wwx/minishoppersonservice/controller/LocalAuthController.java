package com.wwx.minishoppersonservice.controller;

import com.wwx.minishop.entity.LocalAuth;
import com.wwx.minishoppersonservice.service.LocalAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/localAuth")
public class LocalAuthController {

    @Autowired
    LocalAuthService localAuthService;

    @GetMapping("/findLocalAuthWithName/{username}")
    public LocalAuth findLocalAuthWithName(@PathVariable("username")String username){
        return localAuthService.queryLocalAuthWithName(username);
    }

    @PostMapping("/addLocalAuth")
    public Boolean addLocalAuth(@RequestBody LocalAuth localAuth){
        if(localAuth!=null){
            return localAuthService.insertLocalAuth(localAuth) > 0;
        }
        return null;
    }
}
