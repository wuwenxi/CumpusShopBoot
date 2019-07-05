package com.wwx.minishop.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;

/**
 *      商品信息
 *      多表联查 不使用jpa 采用mybatis进行数据查询
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(indexName = "product")
public class Product implements Serializable {
    //商品编号
    @Id
    private Integer productId;
    //商品名
    private String productName;
    //商品详情
    private String productDesc;
    //商品图片地址
    private String imgAddress;
    //原价
    private String normalPrice;
    //折扣价
    private String promotionPrice;
    //权重
    private Integer priority;
    //创建时间
    private Date createTime;
    //更新时间
    private Date lastEditTime;
    //商品状态  可用：下架 ？ 1 ：0
    private Integer enableStatus;
    //商品类别
    private ProductCategory productCategory;
    //所属店铺
    private Shop shop;

}