package com.wwx.minishopusercollection.controller;

import com.wwx.minishop.entity.PersonInfo;
import com.wwx.minishop.entity.Shop;
import com.wwx.minishop.entity.UserCollection;
import com.wwx.minishopusercollection.service.UserCollectionService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserCollectionController {

    @Autowired
    UserCollectionService userCollectionService;

    @PostMapping("/userCollection")
    public Boolean userCollection(@RequestBody UserCollection userCollection){
        if(userCollection!=null){
            return userCollectionService.insertUserCollection(userCollection) > 0;
        }
        return false;
    }

    @GetMapping("/findUserCollection")
    public UserCollection findUserCollectionWithPersonAndShop(@Param("userId")Integer userId,@Param("shopId")Integer shopId){
        if(userId>0&&shopId>0){
            PersonInfo personInfo = new PersonInfo();
            personInfo.setUserId(userId);
            Shop shop = new Shop();
            shop.setShopId(shopId);
            UserCollection userCollection = new UserCollection();
            userCollection.setPersonInfo(personInfo);
            userCollection.setShop(shop);
            return userCollectionService.queryUserCollectionWithPersonAndShop(userCollection);
        }
        return null;
    }

    @GetMapping("/findUserCollectionListWithUserId/{userId}")
    public List<UserCollection> findUserCollectionListWithUserId(@PathVariable("userId")Integer userId){
        if(userId!=null&&userId>0){
            PersonInfo personInfo = new PersonInfo();
            personInfo.setUserId(userId);
            return userCollectionService.queryUserCollectionList(personInfo);
        }
        return null;
    }

    @PostMapping("/cancelCollection")
    public Boolean deleteUserCollectionWithId(@RequestBody UserCollection userCollection){
        if(userCollection!=null){
            return userCollectionService.deleteUserCollection(userCollection) > 0;
        }
        return false;
    }
}
