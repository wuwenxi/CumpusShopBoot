package com.wwx.minishopprovider.controller;

import com.wwx.minishop.entity.ShopCategory;
import com.wwx.minishopprovider.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.security.provider.SHA;

import java.util.List;

@RestController
@RequestMapping("/shopCategory")
public class ShopCategoryController {

    @Autowired
    ShopCategoryService shopCategoryService;

    @GetMapping("/findAllShopCategory")
    public List<ShopCategory> findAllShopCategory(){
        ShopCategory shopCategory = new ShopCategory();
        List<ShopCategory> list = shopCategoryService.findAllShopCategory(shopCategory);
        if(list!=null && list.size()>0){
            return list;
        }
        return null;
    }

    @GetMapping("/findShopCategoryById/{id}")
    public ShopCategory findShopCategoryById(@PathVariable("id")Integer id){
        ShopCategory shopCategory = shopCategoryService.findShopCategoryById(id);
        if(shopCategory!=null){
            return shopCategory;
        }
        return null;
    }

    @GetMapping("/findShopCategoryWithParentId/{id}")
    public List<ShopCategory> findShopCategoryWithParentId(@PathVariable("id")Integer parentId){
        if(parentId!=null && parentId>0){
            ShopCategory shopCategory = new ShopCategory();
            ShopCategory parent = new ShopCategory();
            parent.setShopCategoryId(parentId);
            shopCategory.setParent(parent);
            List<ShopCategory> list = shopCategoryService.findShopCategoryWithParentId(shopCategory);
            if(list!=null && list.size()>0){
                return list;
            }
        }
        return null;
    }
}
