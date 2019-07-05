package com.wwx.minishoppersonservice.controller;

import com.wwx.minishop.entity.PersonInfo;
import com.wwx.minishoppersonservice.service.PersonInfoService;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/personInfo")
public class PersonInfoController {

    @Autowired
    PersonInfoService personInfoService;

    @GetMapping("/findPersonInfoWithUserId/{userId}")
    public PersonInfo findPersonInfoWithUserId(@PathVariable("userId")Integer userId){
        if(userId!=null && userId>0){
            return personInfoService.queryPersonInfoWithUserId(userId);
        }
        return null;
    }

    @GetMapping("/findPersonInfoWithName/{name}")
    public PersonInfo findPersonInfoWithName(@PathVariable("name")String username){
        if(username!=null){
            return personInfoService.queryPersonInfoWithName(username);
        }
        return null;
    }

    @PostMapping("/addPersonInfo")
    public Boolean addPersonInfo(@RequestBody PersonInfo personInfo){
        if(personInfo!=null){
            return personInfoService.insertPersonInfo(personInfo);
        }
        return false;
    }

    @PostMapping("/modifyPersonInfo")
    public Boolean modifyPersonInfo(@RequestBody PersonInfo personInfo){
        if(personInfo!=null&&personInfo.getUserId()!=null){
            return personInfoService.updatePersonInfo(personInfo);
        }
        return false;
    }
}
