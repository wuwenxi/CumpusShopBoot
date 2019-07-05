package com.wwx.minishopelasticsearch.service.impl;

import com.wwx.minishop.entity.Product;
import com.wwx.minishopelasticsearch.repository.ProductRepository;
import com.wwx.minishopelasticsearch.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> queryProductsByProductNameLike(String name) {
        return productRepository.queryProductsByProductNameLike(name);
    }

    @Override
    public void insertProduct(Product product) {
        productRepository.index(product);
    }

    @Override
    public void batchInsertProduct(List<Product> list) {
        productRepository.saveAll(list);
    }

    @Override
    public void updateProduct(Product product) {
        if(productRepository.existsById(product.getProductId())){
            productRepository.deleteById(product.getProductId());
        }
        productRepository.index(product);
    }

    @Override
    public void deleteProduct(Integer id) {
        if(productRepository.existsById(id)){
            productRepository.deleteById(id);
        }
    }
}
