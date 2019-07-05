package com.wwx.minishopprovider.service.impl;

import com.wwx.minishop.entity.ProductImg;
import com.wwx.minishopprovider.dao.ProductImgMapper;
import com.wwx.minishopprovider.service.ProductImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@CacheConfig(cacheManager = "cacheManager")
@Service
public class ProductImgServiceImpl implements ProductImgService {

    @Autowired
    ProductImgMapper productImgMapper;

    @Cacheable(cacheNames = "productImgs",key = "'productImgs'+#productId",unless = "#result==null")
    @Override
    public List<ProductImg> queryProductImgByProductId(Integer productId) {
        return productImgMapper.queryProductImgsByProductId(productId);
    }

    @Override
    public int insertProductImg(List<ProductImg> productImgList) {
        return productImgMapper.insertProductImgs(productImgList);
    }

    @CacheEvict(cacheNames = "productImgs",key = "'productImgs'+#productId")
    @Override
    public int deleteProductImgsByProductId(Integer productId) {
        return productImgMapper.deleteByProductId(productId);
    }
}
