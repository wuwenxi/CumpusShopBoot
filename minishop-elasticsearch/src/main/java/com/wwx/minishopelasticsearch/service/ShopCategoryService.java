package com.wwx.minishopelasticsearch.service;

import com.wwx.minishop.entity.ShopCategory;

import java.util.List;

public interface ShopCategoryService {

    List<ShopCategory> queryShopCategoryLikeName(String shopCategory);

    void updateShopCategory(ShopCategory shopCategory);

    void batchInsert(List<ShopCategory> list);

    void insert(ShopCategory shopCategory);

}
