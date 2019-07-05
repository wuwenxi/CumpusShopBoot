package com.wwx.minishopprovider.dao;

import com.wwx.minishop.entity.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    //商家页面显示所有商品包括不在架的商品
    List<Product> queryAllByShopId(Integer shopId);
    //前端展示在架商品
    List<Product> queryAllByShopIdWithEnableStatus(Integer shopId);

    int updateProduct(Product product);

    Product queryProductById(Integer productId);

    int insertProduct(Product product);
}
