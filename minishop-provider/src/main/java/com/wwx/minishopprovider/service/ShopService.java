package com.wwx.minishopprovider.service;

import com.wwx.minishop.entity.Shop;
import com.wwx.minishop.entity.ShopCategory;

import java.util.List;


public interface ShopService {

    List<Shop> findShopListWithOwner(Shop shop);

    int modifyShop(Shop shop);

    Shop getShopByName(String shopName);

    Shop getShopById(Integer shopId);

    int addShop(Shop shop);

    List<Shop> findShopListWithShopCategory(ShopCategory shopCategory);

    List<Shop> queryShopList();
}
