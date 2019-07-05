package com.wwx.minishopelasticsearch.repository;

import com.wwx.minishop.entity.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ProductRepository extends ElasticsearchRepository<Product,Integer> {

    List<Product> queryProductsByProductNameLike(String name);

}
