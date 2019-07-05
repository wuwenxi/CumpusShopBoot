package com.wwx.minishopprovider.controller;

import com.wwx.minishop.entity.ProductCategory;
import com.wwx.minishopprovider.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productCategory")
public class ProductCategoryController {

    @Autowired
    ProductCategoryService productCategoryService;

    @PostMapping("/addProductCategory")
    public Boolean addProductCategory(@RequestBody ProductCategory productCategory){
        if(productCategory!=null){
            return productCategoryService.addProductCategory(productCategory) > 0;
        }
        return false;
    }

    @PostMapping("/deleteProductCategory")
    public Boolean deleteProductCategory(@RequestBody Integer productCategoryId){
        if(productCategoryId!=null && productCategoryId>0){
            return productCategoryService.deleteProductCategory(productCategoryId) > 0;
        }
        return false;
    }

    @PostMapping("/modifyProductCategory")
    public Boolean modifyProductCategory(@RequestBody ProductCategory productCategory){
        if(productCategory!=null){
            //判断是否更新成功  成功返回true 失败返回false
            return productCategoryService.modifyProductCategory(productCategory) > 0;
        }
        return false;
    }

    @GetMapping("/getCategoryList/{shopId}")
    public List<ProductCategory> getCategoryListWithShopId(@PathVariable("shopId")Integer shopId){
        if(shopId!=null&&shopId>0){
            List<ProductCategory> list = productCategoryService.getCategoryList(shopId);
            if(list!=null && list.size()>0){
                return list;
            }
        }
        return null;
    }
}
