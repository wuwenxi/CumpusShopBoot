package com.wwx.minishopshopmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.wwx.minishop.beans.Msg;
import com.wwx.minishop.entity.PersonInfo;
import com.wwx.minishop.entity.ProductCategory;
import com.wwx.minishop.entity.Shop;
import com.wwx.minishop.utils.HttpServletRequestUtils;
import com.wwx.minishop.utils.PersonInfoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.*;

@RestController
@RequestMapping("/product")
public class ProductCategoryController {

    public static final String REST_URL_CATEGORY_PREFIX = "http://MINISHOP-PROVIDER/productCategory";


    @Autowired
    RestTemplate restTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    @DeleteMapping("/deleteProductCategory/{productCategoryId}")
    public Msg deleteProductCategory(@PathVariable("productCategoryId")Integer productCategoryId,Map<String,Object> map){
        if(productCategoryId>0){
            Boolean flag = restTemplate.postForObject(REST_URL_CATEGORY_PREFIX+"/deleteProductCategory",productCategoryId,Boolean.class);
            if(flag!=null&&flag){
                return Msg.success();
            }
        }
        map.put("msg","删除失败");
        return Msg.fail().add("map",map);
    }

    @PutMapping("/modifyProductCategory")
    public Msg modifyProductCategory(HttpServletRequest request,Map<String,Object> map){
        String categoryStr = HttpServletRequestUtils.getString(request,"productCategory");

        ProductCategory productCategory = null;

        try {
            productCategory = objectMapper.readValue(categoryStr,ProductCategory.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(productCategory!=null && productCategory.getProductCategoryId()>0){
            Boolean flag = restTemplate.postForObject(REST_URL_CATEGORY_PREFIX+"/modifyProductCategory",productCategory,Boolean.class);
            if(flag!=null&&flag){
                return Msg.success();
            }
        }
        map.put("msg","更新失败！");
        return Msg.fail().add("map",map);
    }

    //商品类别类别查询及分页
    @SuppressWarnings("unchecked")
    @GetMapping("/getProductCategoryList/{pn}")
    public Msg getProductCategoryList(HttpServletRequest request, @PathVariable("pn")Integer pn,Map<String,Object> map) throws Exception{
        Shop shop = PersonInfoUtils.getShop(request);
        //分页
        PageMethod.startPage(pn,10);
        /**
         *       查出当前用户下的所有店铺
         *       查询每个店铺下的所有商品类别
         *
         */
        List<ProductCategory> productCategoryList = new ArrayList<>();
        if(shop.getOwner()!=null && shop.getOwner().getUserId()!=null &&
                shop.getOwner().getUserId()>0){

            List<Shop> shopList = null;

            Shop[] shops = restTemplate.getForObject(ShopController.REST_URL_SHOP_PREFIX+"/findShopListWithOwner/"+shop.getOwner().getUserId(),Shop[].class);
            if(shops!=null && shops.length>0){
                shopList = Arrays.asList(shops);
            }
            if(shopList!=null&&shopList.size()>0){
                for (Shop shop1:shopList){
                    List<ProductCategory> productCategories = restTemplate.getForObject(REST_URL_CATEGORY_PREFIX+"/getCategoryList/"+shop1.getShopId(),List.class);
                    if(productCategories!=null)
                        productCategoryList.addAll(productCategories);
                }
                if (productCategoryList.size()>0){
                    PageInfo<ProductCategory> productCategoryPageInfo = new PageInfo<>(productCategoryList,5);
                    map.put("productCategoryPageInfo",productCategoryPageInfo);
                }else {
                    map.put("msg","当前店铺没有商品类别");
                    return Msg.fail().add("map",map);
                }
            }
            return Msg.success().add("map",map);
        }
        map.put("msg","获取商品类别信息失败");
        return Msg.fail().add("map",map);
    }

    @PostMapping("/addProductCategory")
    public Msg addProductCategory(HttpServletRequest request,Map<String,Object> map){

        String productCategoryStr = HttpServletRequestUtils.getString(request,"productCategory");
        ProductCategory productCategory = null;
        try {
            productCategory = objectMapper.readValue(productCategoryStr,ProductCategory.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(productCategory!=null){
            Boolean flag = restTemplate.postForObject(REST_URL_CATEGORY_PREFIX+"/addProductCategory",productCategory,Boolean.class);
            if(flag!=null&&flag){
                return Msg.success();
            }
            map.put("msg","服务器内部错误");
            return Msg.fail().add("map",map);
        }else {
            map.put("msg","类别信息为空");
            return Msg.fail().add("map",map);
        }
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/getcategorylist/{shopId}")
    public Msg getProductInitInfo(@PathVariable("shopId") Integer shopId,Map<String,Object> map){
        List<ProductCategory> productCategories = restTemplate.getForObject(REST_URL_CATEGORY_PREFIX+"/getCategoryList/"+shopId,List.class);
        if (productCategories!=null && productCategories.size()>0){
            map.put("categoryList",productCategories);
            return Msg.success().add("map",map);
        }else {
            map.put("msg","当前店铺没有创建商品类别");
            return Msg.fail().add("map",map);
        }
    }
}
