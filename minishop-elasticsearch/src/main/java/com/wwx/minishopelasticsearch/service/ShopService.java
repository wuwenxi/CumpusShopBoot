package com.wwx.minishopelasticsearch.service;

import com.wwx.minishop.entity.Shop;

import java.util.List;

public interface ShopService {
    //添加店铺
    void insert(Shop shop);
    //批量添加店铺
    void batchInsert(List<Shop> shopList);
    //根据店铺名进行模糊查询
    List<Shop> queryShopByShopNameLike(String shopName);
    //根据店铺类别进行模糊查询
    List<Shop> queryShopByShopCategoryNameLike(String shopCategoryName);
    //根据店铺地址进行模型模糊查询
    List<Shop> queryShopByAreaNameLike(String areaName);
    //更新店铺信息
    void updateShop(Shop shop);
    //根据id删除店铺信息
    void deleteShopById(Integer shopId);
}
