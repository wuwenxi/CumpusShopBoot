package com.wwx.minishop.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Order implements Serializable {

    private Integer orderId;
    // 生成的订单号
    private String orderNumber;
    //收货人
    private String name;
    //电话
    private String phone;
    //收货地址
    private String address;
    //商品
    private Product product;
    //商品数量
    private Integer productCount;
    //店铺
    private Shop shop;
    //订单创建时间
    private Date createTime;
    //订单完成时间
    private Date lastEditTime;
    //订单状态  -1商家为接单  0商家接单  1 订单完成
    private Integer enableStatus;
}
