package com.wwx.minishopprovider;

import com.wwx.minishop.entity.Product;
import com.wwx.minishop.entity.ProductCategory;
import com.wwx.minishop.entity.Shop;
import com.wwx.minishopprovider.dao.ProductMapper;
import com.wwx.minishopprovider.dao.ShopMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MinishopProviderApplicationTests {

    @Autowired
    ProductMapper productMapper;

    @Test
    public void contextLoads() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(1);
        Shop shop = new Shop();
        shop.setShopId(1);
        Product product = new Product(3,"可爱多","好吃美味的冰淇淋，炎热的夏天选购一个吧",null,
                "8.00","8.00",null,new Date(),new Date(),0,productCategory,shop);
        productMapper.insertProduct(product);
        Integer productId = product.getProductId();
        System.out.println(productId);
    }

}
