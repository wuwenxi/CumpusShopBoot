package com.wwx.minishopelasticsearch.service;

import com.wwx.minishop.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> queryProductsByProductNameLike(String name);

    void insertProduct(Product product);

    void batchInsertProduct(List<Product> list);

    void updateProduct(Product product);

    void deleteProduct(Integer id);
}
