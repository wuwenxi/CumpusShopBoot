package com.wwx.minishopelasticsearch;

import com.wwx.minishop.entity.Product;
import com.wwx.minishop.entity.Shop;
import com.wwx.minishop.entity.ShopCategory;
import com.wwx.minishopelasticsearch.repository.*;
import com.wwx.minishopelasticsearch.service.ProductService;
import com.wwx.minishopelasticsearch.service.ShopCategoryService;
import com.wwx.minishopelasticsearch.service.ShopService;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.jms.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MinishopElasticsearchApplicationTests {

    @Autowired
    ShopRepository shopRepository;

    @Test
    public void contextLoads() {
        /*ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(1);
        Shop shop = new Shop(2,null,shopCategory,null,null,null,null,null,null,
                null,null,null,null,null);
        shopRepository.save(shop);*/
        //shopRepository.deleteAll();
        List<Shop> shopList = shopRepository.findByArea_AreaNameLike("理工");
        System.out.println(shopList.size());
        System.out.println(shopList);
    }

    @Test
    public void test01() {
       /* List<Shop> shopList = shopRepository.findByShopCategory_ShopCategoryId(1);
        System.out.println(shopList);*/
    }

    @Autowired
    JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    JmsTemplate jmsTemplate;

    @Test
    public void testActivemq(){
        Destination destination = new ActiveMQQueue("test-mq");
        //Queue queue = new ActiveMQQueue("test-mq");
        System.out.println("发送消息");
        jmsTemplate.send(destination, session -> session.createTextMessage("this is test"));
        //jmsMessagingTemplate.convertAndSend(queue,shop);
        //jmsMessagingTemplate.send(destination,new Me);
        //convertAndSend(Destination destination, Object message)
        //jmsTemplate.convertAndSend(destination,new Shop());
    }

    /*@JmsListener(destination = "test-mq")
    public void receiveMQ(String message){
        System.out.println("接收到消息"+message);
    }*/

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ShopService shopService;

    @Autowired
    ShopCategoryService shopCategoryService;

    @Autowired
    ShopCategoryRepository shopCategoryRepository;

    @SuppressWarnings("unchecked")
    @Test
    public void test(){
        shopCategoryRepository.deleteAll();
        ShopCategory[] shopCategories = restTemplate.getForObject("http://MINISHOP-PROVIDER/shopCategory/findAllShopCategory", ShopCategory[].class);
        //System.out.println(shopCategories);
        shopCategoryService.batchInsert(Arrays.asList(shopCategories));
        /*for (ShopCategory shopCategory:shopCategories){
            shopCategoryService.insert(shopCategory);
            System.out.println(shopCategory);
        }*/
        /*if(shopCategories!=null&&shopCategories.length>0){
            List<ShopCategory> list = Arrays.asList(shopCategories);
            shopCategoryService.batchInsert(list);
        }*/
    }

    @Autowired
    ProductService productService;


    @Test
    public void test02(){
        List<Product> products = productService.queryProductsByProductNameLike("思");
        System.out.println(products);
       /* Product product = restTemplate.getForObject("http://MINISHOP-PROVIDER/product/getProduct/"+2,Product.class);
        if(product!=null){
            productService.insertProduct(product);
        }*/

    }



}
