package com.wwx.minishopelasticsearch.repository;

import com.wwx.minishop.entity.Shop;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ShopRepository extends ElasticsearchRepository<Shop,Integer> {

    List<Shop> findByShopNameLike(String name);

    //List<Shop> findByShopCategory_ShopCategoryId(Integer shopCategoryId);

    List<Shop> findByShopCategory_ShopCategoryNameLike(String shopCategoryName);

    List<Shop> findByArea_AreaNameLike(String areaName);
}
