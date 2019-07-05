package com.wwx.minishopprovider.service;

import com.wwx.minishop.entity.ShopCategory;

import java.util.List;

public interface ShopCategoryService {

    List<ShopCategory> findShopCategoryWithParentId(ShopCategory shopCategory);

    ShopCategory findShopCategoryById(Integer shopCategoryId);

    List<ShopCategory> findAllShopCategory(ShopCategory shopCategory);
}
