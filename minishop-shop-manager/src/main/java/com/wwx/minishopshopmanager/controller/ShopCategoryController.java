package com.wwx.minishopshopmanager.controller;

import com.wwx.minishop.beans.Msg;
import com.wwx.minishop.entity.ShopCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/shop")
public class ShopCategoryController {

    public static final String REST_URL_CATEGORY_PREFIX = "http://MINISHOP-PROVIDER/shopCategory";

    private final RestTemplate restTemplate;

    private Map<String,Object> map = new HashMap<>();

    public ShopCategoryController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/getshopinitinfo/{parentId}")
    public Msg getShopInitInfo( @PathVariable("parentId") Integer parentId){
        if(parentId==null||parentId<0){
            return Msg.fail().add("msg","查询类别错误");
        }

        List<ShopCategory> shopCategoryList = restTemplate.getForObject(REST_URL_CATEGORY_PREFIX+"/findShopCategoryWithParentId/"+parentId,List.class);
        if(shopCategoryList!=null && shopCategoryList.size()>0){
            map.put("shopCategoryList",shopCategoryList);
            return Msg.success().add("map",map);
        }
        return Msg.fail().add("msg","获取商品类别失败");
    }

}
