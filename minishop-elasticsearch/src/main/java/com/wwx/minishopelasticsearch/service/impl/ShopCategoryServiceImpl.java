package com.wwx.minishopelasticsearch.service.impl;

import com.wwx.minishop.entity.ShopCategory;
import com.wwx.minishopelasticsearch.repository.ShopCategoryRepository;
import com.wwx.minishopelasticsearch.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {

    @Autowired
    ShopCategoryRepository shopCategoryRepository;

    @Override
    public List<ShopCategory> queryShopCategoryLikeName(String shopCategoryName) {
        return shopCategoryRepository.findByShopCategoryNameLike(shopCategoryName);
    }

    @Override
    public void updateShopCategory(ShopCategory shopCategory) {
        if(shopCategoryRepository.existsById(shopCategory.getShopCategoryId())){
            shopCategoryRepository.deleteById(shopCategory.getShopCategoryId());
        }
        shopCategoryRepository.index(shopCategory);
    }

    @Override
    public void batchInsert(List<ShopCategory> list) {
        shopCategoryRepository.saveAll(list);
    }

    @Override
    public void insert(ShopCategory shopCategory) {
        shopCategoryRepository.index(shopCategory);
    }
}
