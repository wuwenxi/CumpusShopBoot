package com.wwx.minishop.utils;

import com.wwx.minishop.entity.LocalAuth;
import com.wwx.minishop.entity.PersonInfo;
import com.wwx.minishop.entity.Shop;

import javax.servlet.http.HttpServletRequest;

public class PersonInfoUtils {

    public static PersonInfo getPersonInfo(HttpServletRequest request){
        LocalAuth localAuth = (LocalAuth) request.getSession().getAttribute("localAuth");
        if(localAuth!=null && localAuth.getPersonInfo()!=null){
            request.getSession().setAttribute("personInfo",localAuth.getPersonInfo());
            return localAuth.getPersonInfo();
        }
        return null;
    }

    public static Shop getShop(HttpServletRequest request) throws Exception {
        PersonInfo personInfo = getPersonInfo(request);
        Shop shop = (Shop) Class.forName("com.wwx.minishop.entity.Shop").newInstance();
        shop.setOwner(personInfo);
        return shop;
    }
}
