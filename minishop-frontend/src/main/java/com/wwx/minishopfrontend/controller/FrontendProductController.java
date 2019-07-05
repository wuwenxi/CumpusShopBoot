package com.wwx.minishopfrontend.controller;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.wwx.minishop.beans.Msg;
import com.wwx.minishop.entity.Product;
import com.wwx.minishop.entity.ProductImg;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/frontend")
public class FrontendProductController {

    @Autowired
    RestTemplate restTemplate;

    private static final String REST_URL_PRODUCT_PREFIX = "http://MINISHOP-PROVIDER/product";

    private static final String REST_URL_PRODUCT_IMG_PREFIX = "http://MINISHOP-PROVIDER/productImg";

    @SuppressWarnings("unchecked")
    @GetMapping("/getProductList/{id}")
    public Msg getProductList(@PathVariable("id")Integer id, @Param("pn")Integer pn, Map<String,Object> map){
        PageMethod.startPage(pn,10);

        List<Product> list = restTemplate.getForObject(REST_URL_PRODUCT_PREFIX +"/findProductListWithShopIdAndEnableStatus/"+id,List.class);
        if(list!=null&&list.size()>0){
            PageInfo<Product> pageInfo = new PageInfo<>(list,5);
            map.put("pageInfo",pageInfo);
            return Msg.success().add("map",map);
        }
        return Msg.fail().add("msg","当前店铺未创建任何商品");
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/getProduct/{productId}")
    public Msg getProduct(@PathVariable("productId")Integer productId,Map<String,Object> map){
        if(productId>0){
            Product product = restTemplate.getForObject(REST_URL_PRODUCT_PREFIX+"/getProduct/"+productId,Product.class);
            if(product!=null){
                List<ProductImg> list = restTemplate.getForObject(REST_URL_PRODUCT_IMG_PREFIX+"/getProductImgByProductId/"+product.getProductId(),List.class);
                if(list != null && list.size()>0)
                    map.put("list",list);
                map.put("product",product);
                return Msg.success().add("map",map);
            }
        }
        return Msg.fail().add("msg","查找失败");
    }

}
