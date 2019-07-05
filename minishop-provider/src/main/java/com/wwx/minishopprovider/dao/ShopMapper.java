package com.wwx.minishopprovider.dao;

import com.wwx.minishop.entity.Shop;
import com.wwx.minishop.entity.ShopCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShopMapper {

    List<Shop> selectShopList(@Param("shop") Shop shop, @Param("rowIndex") Integer rowIndex, @Param("pageSize") Integer pageSize);

    int updateShop(Shop shop);

    int insertShop(Shop shop);

    Shop queryShopById(Integer shopId);

    List<Shop> queryShopsByShopCategoryId(@Param("shopCategory") ShopCategory shopCategory);

    List<Shop> queryShopList();

    Shop queryShopByName(String shopName);

}
