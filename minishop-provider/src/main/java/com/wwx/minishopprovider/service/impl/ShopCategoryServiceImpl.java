package com.wwx.minishopprovider.service.impl;

import com.wwx.minishop.entity.ShopCategory;
import com.wwx.minishopprovider.dao.ShopCategoryMapper;
import com.wwx.minishopprovider.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@CacheConfig(cacheManager = "cacheManager")
@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {

    @Autowired
    ShopCategoryMapper shopCategoryMapper;

    @Cacheable(value = "shopCategoryWithParent",key = "'parentId'+#shopCategory.parent.shopCategoryId",unless = "#result == null ")
    @Override
    public List<ShopCategory> findShopCategoryWithParentId(ShopCategory shopCategory) {
        return shopCategoryMapper.queryForListShopCategory(shopCategory);
    }

    @Cacheable(value = "shopCategory",key = "'shopCategroyId'+#shopCategoryId",unless = "#result == null ")
    @Override
    public ShopCategory findShopCategoryById(Integer shopCategoryId) {
        return shopCategoryMapper.queryShopCategory(shopCategoryId);
    }

    @Cacheable(value = "allShopCategory",key = "'allShopCategory'",unless = "#result == null ")
    @Override
    public List<ShopCategory> findAllShopCategory(ShopCategory shopCategory) {
        return shopCategoryMapper.queryForListShopCategory(shopCategory);
    }
}
