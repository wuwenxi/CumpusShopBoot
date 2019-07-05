package com.wwx.minishop.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
/**
 *    用户收藏
 *      用户信息，店铺信息
 */
public class UserCollection implements Serializable {

    private Integer userCollectionId;

    private PersonInfo personInfo;

    private Shop shop;

    private Date createTime;
}
