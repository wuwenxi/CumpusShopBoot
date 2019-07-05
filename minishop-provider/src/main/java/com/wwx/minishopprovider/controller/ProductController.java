package com.wwx.minishopprovider.controller;

import com.wwx.minishop.entity.Product;
import com.wwx.minishopprovider.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/findProductListWithShopId/{id}")
    public List<Product> findProductListWithShopId(@PathVariable("id")Integer id){
        if(id >0){
            List<Product> list = productService.queryProductListWithShopId(id);
            if(list!=null && list.size()>0){
                return list;
            }
        }
        return null;
    }

    @GetMapping("/findProductListWithShopIdAndEnableStatus/{id}")
    public List<Product> findProductListWithShopIdAndEnableStatus(@PathVariable("id")Integer id){
        if(id>0){
            List<Product> list = productService.queryAllByShopIdWithEnableStatus(id);
            if(list!=null && list.size()>0){
                return list;
            }
        }
        return null;
    }

    @GetMapping("/getProduct/{productId}")
    public Product getProduct(@PathVariable("productId")Integer productId){
        if(productId>0){
            Product product = productService.queryProductById(productId);
            if(product!=null){
                return product;
            }
        }
        return null;
    }

    @PostMapping("/addProduct")
    public Boolean addProduct(@RequestBody Product product){
        if(product!=null){
            return productService.insertProduct(product)>0;
        }
        return false;
    }

    @PostMapping("/modifyProduct")
    public Boolean modifyProduct(@RequestBody Product product){
        if(product!=null){
            return productService.updateProduct(product)>0;
        }
        return false;
    }
}
