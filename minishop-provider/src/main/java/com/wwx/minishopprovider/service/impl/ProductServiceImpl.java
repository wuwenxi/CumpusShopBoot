package com.wwx.minishopprovider.service.impl;

import com.wwx.minishop.entity.Product;
import com.wwx.minishopprovider.dao.ProductMapper;
import com.wwx.minishopprovider.service.ProductService;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import java.util.Date;
import java.util.List;

@CacheConfig(cacheManager = "cacheManager")
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;

    private final JmsTemplate jmsTemplate;

    public ProductServiceImpl(ProductMapper productMapper, JmsTemplate jmsTemplate) {
        this.productMapper = productMapper;
        this.jmsTemplate = jmsTemplate;
    }

    @Cacheable(value = "productList",key = "'shopId'+#id",unless = "#result == null ")
    @Override
    public List<Product> queryProductListWithShopId(Integer id) {
        return productMapper.queryAllByShopId(id);
    }

    @Cacheable(cacheNames = "productListWithEnableStatus",key = "'shopId'+#id",unless = "#result == null ")
    @Override
    public List<Product> queryAllByShopIdWithEnableStatus(Integer id) {
        return productMapper.queryAllByShopIdWithEnableStatus(id);
    }

    @Cacheable(value = "product",key = "'product'+#productId",unless = "#result == null ")
    @Override
    public Product queryProductById(Integer productId) {
        return productMapper.queryProductById(productId);
    }

    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "productList",key = "'shopId'+#product.shop.shopId"),
                    @CacheEvict(cacheNames = "productListWithEnableStatus",key = "'shopId'+#product.shop.shopId")
            }
    )
    @Override
    public int insertProduct(Product product) {
        product.setCreateTime(new Date());
        product.setLastEditTime(new Date());
        product.setEnableStatus(0);
        int num = productMapper.insertProduct(product);
        if(num > 0){
            Destination destination = new ActiveMQQueue("product");
            jmsTemplate.send(destination,session -> session.createTextMessage(product.getProductId()+""));
        }
        return num;
    }

    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "productList",key = "'shopId'+#product.shop.shopId"),
                    @CacheEvict(cacheNames = "productListWithEnableStatus",key = "'shopId'+#product.shop.shopId")
            },
            put = {
                    @CachePut(cacheNames = "product",key = "'product'+#product.productId")
            }
    )
    @Override
    public int updateProduct(Product product) {
        product.setLastEditTime(new Date());
        int num = productMapper.updateProduct(product);
        if(num > 0){
            Destination destination = new ActiveMQQueue("product-modify");
            jmsTemplate.send(destination,session -> session.createTextMessage(product.getProductId()+""));
        }
        return num;
    }
}
