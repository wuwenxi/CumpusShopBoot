package com.wwx.minishopfrontend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wwx.minishop.beans.Msg;
import com.wwx.minishop.entity.UserCollection;
import com.wwx.minishop.utils.HttpServletRequestUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/frontend")
public class FrontendUserCollectionController {

    private static final String REST_URL_COLL_PREFIX = "http://MINISHOP-USER-COLLECTION";

    @Autowired
    RestTemplate restTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/userCollection")
    public Msg userCollection(HttpServletRequest request){
        String userCollectionStr = HttpServletRequestUtils.getString(request,"collection");
        UserCollection userCollection;

        try {
            userCollection = objectMapper.readValue(userCollectionStr,UserCollection.class);
        } catch (IOException e) {
            return Msg.fail().add("msg","服务器内部错误");
        }

        if(userCollection==null || userCollection.getPersonInfo()==null || userCollection.getShop() == null){
            return Msg.fail().add("msg","店铺收藏失败");
        }

        Boolean flag = restTemplate.postForObject(REST_URL_COLL_PREFIX+"/userCollection",userCollection,Boolean.class);
        if(flag!=null&&flag){
            return Msg.success();
        }

        return Msg.fail().add("msg","店铺收藏失败");
    }

    @PostMapping("/cancelCollection")
    public Msg cancelCollection(HttpServletRequest request){
        String userCollectionStr = HttpServletRequestUtils.getString(request,"collection");
        UserCollection userCollection;

        try {
            userCollection = objectMapper.readValue(userCollectionStr,UserCollection.class);
        } catch (IOException e) {
            return Msg.fail().add("msg","服务器内部错误");
        }

        if(userCollection==null || userCollection.getPersonInfo()==null || userCollection.getShop() == null){
            return Msg.fail().add("msg","取消收藏失败");
        }

        Boolean flag = restTemplate.postForObject(REST_URL_COLL_PREFIX+"/cancelCollection",userCollection,Boolean.class);
        if(flag!=null&&flag){
            return Msg.success();
        }

        return Msg.fail().add("msg","取消收藏失败");
    }

    @GetMapping("/findUserCollection")
    public Msg findUserCollection(@Param("userId")Integer userId,@Param("shopId")Integer shopId){
         if(userId!=null && userId >0 && shopId!=null && shopId>0){
             Map<String,Object> map = new HashMap<>();
             map.put("userId",userId);
             map.put("shopId",shopId);
             UserCollection userCollection = restTemplate.getForObject(REST_URL_COLL_PREFIX + "/findUserCollection?userId={userId}&shopId={shopId}", UserCollection.class, map);
             if(userCollection!=null){
                 return Msg.success().add("userCollection",userCollection);
             }
         }
         return Msg.fail();
    }
}
