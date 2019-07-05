package com.wwx.minishopelasticsearch.service.impl;

import com.wwx.minishop.entity.Shop;
import com.wwx.minishopelasticsearch.repository.ShopRepository;
import com.wwx.minishopelasticsearch.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    ShopRepository shopRepository;

    @Override
    public void insert(Shop shop) {
        shopRepository.index(shop);
    }

    @Override
    public void batchInsert(List<Shop> shopList) {
        shopRepository.saveAll(shopList);
    }

    @Override
    public List<Shop> queryShopByShopNameLike(String shopName) {
        return shopRepository.findByShopNameLike(shopName);
    }

    @Override
    public List<Shop> queryShopByShopCategoryNameLike(String shopCategoryName) {
        return shopRepository.findByShopCategory_ShopCategoryNameLike(shopCategoryName);
    }

    @Override
    public List<Shop> queryShopByAreaNameLike(String areaName) {
        return shopRepository.findByArea_AreaNameLike(areaName);
    }

    @Override
    public void updateShop(Shop shop) {
        if(shopRepository.existsById(shop.getShopId())){
            shopRepository.deleteById(shop.getShopId());
        }
        shopRepository.index(shop);
    }

    @Override
    public void deleteShopById(Integer shopId) {
        if(shopRepository.existsById(shopId)){
            shopRepository.deleteById(shopId);
        }
    }
}
