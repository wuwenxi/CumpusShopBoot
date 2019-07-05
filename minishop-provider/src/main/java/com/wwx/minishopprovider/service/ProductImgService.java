package com.wwx.minishopprovider.service;

import com.wwx.minishop.entity.ProductImg;
import org.omg.CORBA.INTERNAL;

import java.util.List;

public interface ProductImgService {

    List<ProductImg> queryProductImgByProductId(Integer productId);

    int insertProductImg(List<ProductImg> productImgList);

    int deleteProductImgsByProductId(Integer productId);
}
