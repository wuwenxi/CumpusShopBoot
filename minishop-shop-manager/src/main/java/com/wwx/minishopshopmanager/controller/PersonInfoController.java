package com.wwx.minishopshopmanager.controller;

import com.wwx.minishop.beans.Msg;
import com.wwx.minishop.entity.LocalAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/personInfo")
public class PersonInfoController {

    @Autowired
    RestTemplate restTemplate;

    public static final String REST_URL_PERSON_PREFIX = "http://MINISHOP-PERSON-SERVICE/localAuth";

    private Map<String,Object> map = new HashMap<>();

    @GetMapping("/initPersonInfo")
    public Msg initPersonInfo(HttpServletRequest request){
        LocalAuth localAuth = (LocalAuth) request.getSession().getAttribute("localAuth");
        if(localAuth!=null){
            request.getSession().setAttribute("localAuth",localAuth);
            map.put("personInfo",localAuth.getPersonInfo());
            return Msg.success().add("map",map);
        }else {
            //获取用户名
            String localAuthName = request.getUserPrincipal().getName();
            if(localAuthName!=null){
                localAuth = restTemplate.getForObject(REST_URL_PERSON_PREFIX+"/findLocalAuthWithName/"+localAuthName,LocalAuth.class);
                if(localAuth!=null){
                    request.getSession().setAttribute("localAuth",localAuth);
                    map.put("personInfo",localAuth.getPersonInfo());
                    return Msg.success().add("map",map);
                }
            }
            map.put("msg","未登录");
            return Msg.fail().add("map",map);
        }
    }

}
