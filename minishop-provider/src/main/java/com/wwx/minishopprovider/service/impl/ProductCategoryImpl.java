package com.wwx.minishopprovider.service.impl;

import com.wwx.minishop.entity.ProductCategory;
import com.wwx.minishopprovider.dao.ProductCategoryMapper;
import com.wwx.minishopprovider.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@CacheConfig(cacheManager = "cacheManager")
@Service
public class ProductCategoryImpl implements ProductCategoryService {

    @Autowired
    ProductCategoryMapper productCategoryMapper;

    @Cacheable(cacheNames = "productCategoryList",key = "'shopId'+#shopId",unless = "#result == null")
    @Override
    public List<ProductCategory> getCategoryList(Integer shopId) {
        return productCategoryMapper.queryProductCategoriesByShopId(shopId);
    }

    @CacheEvict(cacheNames = "productCategoryList",key = "'shopId'+#productCategory.shop.shopId")
    @Override
    public int addProductCategory(ProductCategory productCategory) {
        return productCategoryMapper.insertProductCategory(productCategory);
    }

    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "productCategoryList",
                            key ="'shopId'+#productCategory.shop.shopId")
            }
    )
    @Override
    public int modifyProductCategory(ProductCategory productCategory) {
        return productCategoryMapper.updateProductCategory(productCategory);
    }

    @CacheEvict(cacheNames = "productCategoryList",
            key = "'shopId'+#productCategoryImpl.queryProductCategory(#productCategoryId).shop.shopId")
    @Override
    public int deleteProductCategory(Integer productCategoryId) {
        return productCategoryMapper.deleteByProductCategoryId(productCategoryId);
    }

    @Cacheable(cacheNames = "productCategory",key = "'productCategoryId'+#productCategoryId",unless = "#result==null")
    @Override
    public ProductCategory queryProductCategory(Integer productCategoryId) {
        return productCategoryMapper.queryProductCategory(productCategoryId);
    }
}
