package com.wwx.minishopprovider.controller;

import com.wwx.minishop.entity.PersonInfo;
import com.wwx.minishop.entity.Shop;
import com.wwx.minishop.entity.ShopCategory;
import com.wwx.minishopprovider.dao.ShopCategoryMapper;
import com.wwx.minishopprovider.service.ShopService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    ShopService shopService;

    @GetMapping("/findShopList")
    public List<Shop> findShopList(){
        List<Shop> list = shopService.queryShopList();
        if(list!=null){
            return list;
        }
        return null;
    }

    @GetMapping("/findShopListWithShopCategoryId/{id}")
    public List<Shop> findShopListWithShopCategoryId(@PathVariable("id")Integer shopCategoryId){
        if(shopCategoryId!=null&&shopCategoryId>0){
            ShopCategory shopCategory = new ShopCategory();
            shopCategory.setShopCategoryId(shopCategoryId);
            List<Shop> shopList = shopService.findShopListWithShopCategory(shopCategory);
            if(shopList!=null && shopList.size()>0){
                return shopList;
            }
        }
        return null;
    }

    @GetMapping("/getShopByName")
    public Shop getShopByName(@Param("name") String shopName){
        if(shopName!=null){
            Shop shop = shopService.getShopByName(shopName);
            if(shop!=null){
                return shop;
            }
        }
        return null;
    }

    @GetMapping("/getShopById/{id}")
    public Shop getShopById(@PathVariable("id")Integer shopId){
        if(shopId!=null&&shopId>0){
            Shop shop = shopService.getShopById(shopId);
            if(shop!=null){
                return shop;
            }
        }
        return null;
    }

    @GetMapping("/findShopListWithOwner/{userId}")
    public List<Shop> findShopListWithOwner(@PathVariable("userId")Integer userId){
        if(userId!=null&&userId>0){
            PersonInfo personInfo = new PersonInfo();
            personInfo.setUserId(userId);
            Shop shop = new Shop();
            shop.setOwner(personInfo);
            List<Shop> list = shopService.findShopListWithOwner(shop);
            if(list!=null&&list.size()>0){
                return list;
            }
        }
        return null;
    }

    @PostMapping("/modifyShop")
    public Shop modifyShop(@RequestBody Shop shop){
        if(shop!=null){
            if(shopService.modifyShop(shop) > 0){
                return shop;
            }
        }
        return null;
    }

    @PostMapping("/addShop")
    public Shop addShop(@RequestBody Shop shop){
        if(shop!=null){
            if(shopService.addShop(shop) > 0){
                return shop;
            }
        }
        return null;
    }
}
