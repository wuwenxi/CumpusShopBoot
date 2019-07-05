package com.wwx.minishopprovider.service.impl;

import com.wwx.minishop.entity.Shop;
import com.wwx.minishop.entity.ShopCategory;
import com.wwx.minishopprovider.dao.ShopMapper;
import com.wwx.minishopprovider.service.ShopService;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import java.util.Date;
import java.util.List;

@CacheConfig(cacheManager = "cacheManager")
@Service
public class ShopServiceImpl implements ShopService {

    private final ShopMapper shopMapper;

    private final JmsTemplate jmsTemplate;

    @Autowired
    public ShopServiceImpl(JmsTemplate jmsTemplate, ShopMapper shopMapper) {
        this.jmsTemplate = jmsTemplate;
        this.shopMapper = shopMapper;
    }

    @Cacheable(cacheNames = "shopListWithOwner",key = "'owner'+#shop.owner.userId",unless = "#result==null")
    @Override
    public List<Shop> findShopListWithOwner(Shop shop) {
        //根据用户查询店铺   每个用户只允许有不超过10的店铺
        return shopMapper.selectShopList(shop,0,10);
    }

    @Caching(
            put = {
                    @CachePut(cacheNames = "shop",key = "'shopId'+#shop.shopId")
            },
            evict = {
                    @CacheEvict(cacheNames = "shopListWithOwner",key = "'owner'+#shop.owner.userId"),
                    @CacheEvict(cacheNames = "shopListWithShopCategory",key = "'shopCategory'+#shop.shopCategory.shopCategoryId")
            }
    )
    @Override
    public int modifyShop(Shop shop) {
        int num = shopMapper.updateShop(shop);
        if(num>0){
            Destination destination = new ActiveMQQueue("shop-modify");
            jmsTemplate.send(destination,session -> session.createTextMessage(shop.getShopId()+""));
            return num;
        }
        return num;

    }

    @Override
    public Shop getShopByName(String shopName) {
        return shopMapper.queryShopByName(shopName);
    }

    @Cacheable(value = "shop",key = "'shopId'+#shopId",unless = "#result == null ")
    @Override
    public Shop getShopById(Integer shopId) {
        return shopMapper.queryShopById(shopId);
    }

    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "shopListWithOwner",key = "'owner'+#shop.owner.userId"),
                    @CacheEvict(cacheNames = "shopListWithShopCategory",key = "'shopCategory'+#shop.shopCategory.shopCategoryId")
            }
    )
    @Override
    public int addShop(Shop shop) {
        shop.setCreateTime(new Date());
        shop.setLastEditTime(new Date());
        shop.setEnableStatus(-1);
        int num = shopMapper.insertShop(shop);
        if( num > 0){
            Destination destination = new ActiveMQQueue("shop");
            jmsTemplate.send(destination, session -> session.createTextMessage(shop.getShopId()+""));
        }
        return num;
    }

    @Cacheable(value = "shopListWithShopCategory",key = "'shopCategory'+#shopCategory.shopCategoryId",unless = "#result == null")
    @Override
    public List<Shop> findShopListWithShopCategory(ShopCategory shopCategory) {
        return shopMapper.queryShopsByShopCategoryId(shopCategory);
    }

    @Override
    public List<Shop> queryShopList() {
        return shopMapper.queryShopList();
    }
}
