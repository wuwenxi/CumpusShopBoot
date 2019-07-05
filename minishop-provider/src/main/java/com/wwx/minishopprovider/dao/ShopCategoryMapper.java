package com.wwx.minishopprovider.dao;

import com.wwx.minishop.entity.ShopCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShopCategoryMapper {

    List<ShopCategory> queryForListShopCategory(@Param("shopCategory") ShopCategory shopCategory);

    ShopCategory queryShopCategory(Integer shopCategoryId);

}
