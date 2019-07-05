package com.wwx.minishopprovider.service;

import com.wwx.minishop.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> queryProductListWithShopId(Integer id);

    List<Product> queryAllByShopIdWithEnableStatus(Integer id);

    Product queryProductById(Integer productId);

    int insertProduct(Product product);

    int updateProduct(Product product);
}
