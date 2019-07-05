package com.wwx.minishopelasticsearch.repository;

import com.wwx.minishop.entity.ShopCategory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ShopCategoryRepository extends ElasticsearchRepository<ShopCategory,Integer> {

    List<ShopCategory> findByShopCategoryNameLike(String shopCategoryName);
}
