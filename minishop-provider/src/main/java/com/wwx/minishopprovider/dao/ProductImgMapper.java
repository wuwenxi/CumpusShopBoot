package com.wwx.minishopprovider.dao;

import com.wwx.minishop.entity.ProductImg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductImgMapper {

    List<ProductImg> queryProductImgsByProductId(Integer id);

    int insertProductImgs(@Param("list") List<ProductImg> products);

    int deleteByProductId(Integer productId);
}
