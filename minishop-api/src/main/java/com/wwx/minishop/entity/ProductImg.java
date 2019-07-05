package com.wwx.minishop.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 *   商品详情图
 * */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class  ProductImg implements Serializable {
    //商品图片id
    private Integer productImgId;
    //图片地址
    private String imgAddress;
    //图片详情
    private String imgDesc;
    //权值
    private Integer priority;
    //创建时间
    private Date createTime;
    //商品id
    private Integer productId;

}