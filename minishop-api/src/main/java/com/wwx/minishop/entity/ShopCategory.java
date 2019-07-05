package com.wwx.minishop.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;

/**
 *  店铺类别  如：奶茶店、超市等
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(indexName = "shopcategory")
public class ShopCategory implements Serializable {
    //类别编号
    @Id
    private Integer shopCategoryId;
    //类别名称
    private String shopCategoryName;
    //详细情况
    private String shopCategoryDesc;
    //权重
    private Integer priority;
    //创建时间
    private Date createTime;
    //更新时间
    private Date lastEditTime;
    /**
     *    父级店铺类别id  ,由于父级店铺类别也是ShopCategory,创建类型为ShopCategory
     */
    private ShopCategory parent;

}