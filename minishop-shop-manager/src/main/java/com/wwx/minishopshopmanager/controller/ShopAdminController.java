package com.wwx.minishopshopmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shopAdmin")
public class ShopAdminController {

    private static final String prefix = "shopAdmin/";//前缀prefix  后缀 suffix

    @GetMapping()
    public String ShopAdminIndex(){
        return "shopAdmin/ShopAdmin";
    }

    @GetMapping("/shoplist")
    public String ShopList(){
        return prefix + "ShopList";
    }

    @GetMapping("/order")
    public String Order(){
        return prefix + "order";
    }

    @GetMapping("/productlist")
    public String ProductList(){
        return prefix +"ProductList";
    }

    @GetMapping("/productcategory")
    public String productCategory(){
        return prefix + "ProductCategoryList";
    }

    @GetMapping("/addshop")
    public String addShop(){
        return prefix + "AddShop";
    }

    @GetMapping("/addproduct")
    public String addProduct(){
        return prefix + "AddProduct";
    }

    @GetMapping("/addproductcategory")
    public String addProductCategory(){
        return prefix + "AddProductCategory";
    }

    @GetMapping("/userinfo")
    public String UserInfo(){
        return prefix + "UserInfo";
    }

    @GetMapping("/feedback")
    public String Feedback(){
        return prefix + "Feedback";
    }

    @GetMapping("/shopdetail")
    public String ShopDetail(){
        return prefix + "ShopDetail";
    }

    @GetMapping("/modifyProduct")
    public String modifyProduct(){
        return prefix + "modifyProduct";
    }

    @GetMapping("/modifyShop")
    public String modifyShop(){return prefix + "modifyShop";}

}
