package com.wwx.minishop.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;

/**
 *    店铺
 * */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(indexName = "shop")
public class Shop implements Serializable {

    private static final long serialVersionUID = 1L;
    //店铺id
    @Id
    private Integer shopId;
    //店家id
    private PersonInfo owner;
    //商铺类别id
    private ShopCategory shopCategory;
    //店铺名
    private String shopName;
    //店铺描述
    private String shopDesc;
    //店铺地址
    private String shopAddress;
    //联系方式
    private String phone;
    //店铺图片地址
    private String shopImg;
    //权值
    private Integer priority;
    //创建时间
    private Date createTime;
    //更新时间
    private Date lastEditTime;
    //状态   可用:1  不可用:-1  审核:0
    private Integer enableStatus;
    //超级管理员建议
    private String advice;

    private Area area;

}