package com.wwx.minishopfrontend.controller;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.wwx.minishop.beans.Msg;
import com.wwx.minishop.entity.Shop;
import com.wwx.minishop.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/frontend")
public class FrontendShopController {

    @Autowired
    RestTemplate restTemplate;

    private static final String REST_URL_CATEGORY_PREFIX = "http://MINISHOP-PROVIDER/shopCategory";

    private static final String REST_URL_SHOP_PREFIX = "http://MINISHOP-PROVIDER/shop";

    @SuppressWarnings("unchecked")
    @GetMapping("/initShopCategory")
    public Msg initShopCategory(Map<String,Object> map){
        List<ShopCategory> list = restTemplate.getForObject(REST_URL_CATEGORY_PREFIX+"/findAllShopCategory",List.class);
        if(list!=null && list.size()>0){
            map.put("categoryList",list);
            return Msg.success().add("map",map);
        }else {
            return Msg.fail().add("msg","没有商品类别");
        }
    }

    /**
     *
     *                   根据子类别查询店铺
     * @param shopCategoryId
     * @return
     */
    @SuppressWarnings("unchecked")
    @GetMapping("/initWithShopCategory/{shopCategoryId}")
    public Msg initWithShopCategory(@PathVariable("shopCategoryId") Integer shopCategoryId,@Param("pn")Integer pn,Map<String,Object> map){

        PageMethod.startPage(pn,20);

        ShopCategory shopCategory = new ShopCategory();
        //查询当前子类别下的所有店铺
        shopCategory.setShopCategoryId(shopCategoryId);
        //查询当前类别
        shopCategory = restTemplate.getForObject(REST_URL_CATEGORY_PREFIX+"/findShopCategoryById/"+shopCategoryId,ShopCategory.class);
        //根据类别id查出店铺列表
        List<Shop> list = restTemplate.getForObject(REST_URL_SHOP_PREFIX+"/findShopListWithShopCategoryId/"+shopCategoryId,List.class);

        map.put("shopCategory",shopCategory);

        //前端对list进行判断是否为空
        if(list ==null){
            list = new ArrayList<>();
        }
        PageInfo<Shop> pageInfo = new PageInfo<>(list,5);
        map.put("pageInfo",pageInfo);

        return Msg.success().add("map",map);
    }

    /**
     *
     *                 根据一级类别查询店铺
     * @param parentId
     * @return
     */
    @SuppressWarnings("unchecked")
    @GetMapping("/initShopWithParentId/{id}")
    public Msg initShopWithParentId(@PathVariable("id")Integer parentId,@Param("pn")Integer pn,Map<String,Object> map){

        //每一页最多店铺数
        PageMethod.startPage(pn,20);

        //1. 查出parentId下的所有子类别
        List<ShopCategory> ShopCategoryList = new ArrayList<>();
        ShopCategory[] shopCategories = restTemplate.getForObject(REST_URL_CATEGORY_PREFIX+"/findShopCategoryWithParentId/"+parentId,ShopCategory[].class);
        if(shopCategories!=null && shopCategories.length>0){
            ShopCategoryList = Arrays.asList(shopCategories);
        }
        //List<ShopCategory> shopCategories = restTemplate.getForObject(REST_URL_CATEGORY_PREFIX+"/findShopCategoryWithParentId/"+parentId,List.class);
        //2.查出每个子类别下的所有店铺
        List<Shop> shops = new ArrayList<>();
        if(shopCategories!=null){
            for (ShopCategory shopCategory:ShopCategoryList){
                //根据类别id查出所有店铺
                List<Shop> list = restTemplate.getForObject(REST_URL_SHOP_PREFIX+"/findShopListWithShopCategoryId/"+shopCategory.getShopCategoryId(),List.class);
                if(list!=null && list.size()>0){
                    shops.addAll(list);
                }
            }
        }

        if(shops.size() > 0){
            PageInfo<Shop> pageInfo = new PageInfo<>(shops,5);
            map.put("pageInfo",pageInfo);
            return Msg.success().add("map",map);
        }else {
            return Msg.fail().add("msg","当前类别下没有店铺");
        }
    }

    /**
     *
     *         获取店铺信息
     * @param id
     * @return
     */
    @GetMapping("/getShop/{id}")
    public Msg getShop(@PathVariable("id")Integer id,Map<String,Object> map){
        //根据店铺id获取店铺信息
        Shop shop = restTemplate.getForObject(REST_URL_SHOP_PREFIX + "/getShopById/" + id, Shop.class);
        map.put("shop",shop);
        return Msg.success().add("map",map);
    }
}
