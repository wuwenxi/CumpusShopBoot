package com.wwx.minishop.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 *    商品类别
 * */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductCategory implements Serializable {
    //商品Id
    private Integer productCategoryId;
    //商品名
    private String productCategoryName;
    //权值
    private Integer priority;
    //创建时间
    private Date createTime;
    //商铺id
    private Shop shop;

}