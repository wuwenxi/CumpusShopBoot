package com.wwx.minishopprovider.controller;

import com.wwx.minishop.entity.ProductImg;
import com.wwx.minishopprovider.service.ProductImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productImg")
public class ProductImgController {

    @Autowired
    ProductImgService productImgService;

    @GetMapping("/getProductImgByProductId/{productId}")
    public List<ProductImg> getProductImgByProductId(@PathVariable("productId")Integer productId){
        if(productId!=null && productId > 0){
            return productImgService.queryProductImgByProductId(productId);
        }
        return null;
    }

    @PostMapping("/addProductImgs")
    public Boolean addProductImgs(@RequestBody List<ProductImg> productImgList){
        if(productImgList != null){
            return productImgService.insertProductImg(productImgList) > 0;
        }
        return false;
    }

    @PostMapping("/deleteProductImgsByProductId")
    public Boolean deleteProductImgsByProductId(@RequestBody Integer productId){
        if(productId!=null&&productId>0){
            return productImgService.deleteProductImgsByProductId(productId) > 0;
        }
        return false;
    }
}
