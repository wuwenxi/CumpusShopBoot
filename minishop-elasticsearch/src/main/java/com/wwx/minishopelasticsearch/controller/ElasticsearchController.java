package com.wwx.minishopelasticsearch.controller;

import com.wwx.minishop.entity.Area;
import com.wwx.minishop.entity.Shop;
import com.wwx.minishop.entity.ShopCategory;
import com.wwx.minishopelasticsearch.service.ShopCategoryService;
import com.wwx.minishopelasticsearch.service.ShopService;
import com.wwx.minishopelasticsearch.service.AreaService;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/es")
public class ElasticsearchController {

    private final ShopService shopService;

    private final ShopCategoryService shopCategoryService;

    private final AreaService areaService;

    public ElasticsearchController(ShopService shopService, ShopCategoryService shopCategoryService, AreaService areaService){
        this.shopService = shopService;
        this.shopCategoryService = shopCategoryService;
        this.areaService = areaService;
    }


    /**
     *          根据名字模糊查询店铺
     *              按店铺名查
     *
     * @param name
     * @return
     */
    @GetMapping("/findShopWithName")
    public List<Shop> findShopWithName(@Param("name")String name){
        if(name!=null){
            List<Shop> list = shopService.queryShopByShopNameLike(name);
            if(list!=null&&list.size()>6){
                //截取下标0,5的list
                return list.subList(0,6);
            }else {
                return list;
            }
        }
        return null;
    }

    @GetMapping("/findShopCategoryWithName")
    public List<ShopCategory> findShopCategoryWithName(@Param("name")String shopCategoryName){
        if(shopCategoryName!=null){
            List<ShopCategory> list = shopCategoryService.queryShopCategoryLikeName(shopCategoryName);
            if(list!=null&&list.size()>2){
                return list.subList(0,2);
            }else {
                return list;
            }
        }
        return null;
    }

    @GetMapping("/findAreaWithName")
    public List<Area> findAreaWithName(@Param("name")String areaName){
        if(areaName!=null){
            List<Area> list = areaService.queryAreaWithName(areaName);
            if(list!=null&&list.size()>2){
                return list.subList(0,2);
            }else {
                return list;
            }
        }
        return null;
    }


}
