package com.wwx.minishop.entity;



import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 *    头条展示
 *
 *    lombok  添加getter、setter、toString、无参构造、全参构造
 */
@Getter
@AllArgsConstructor
@Setter
@ToString
@NoArgsConstructor
public class HeadLine implements Serializable {
    //头条id
    private Integer lineId;
    //头条名字
    private String lineName;
    //头条链接
    private String lineLink;
    //头条图片
    private String lineImg;
    //权重(权值越大，展示越靠前)
    private Integer priority;
    //可显示：不可显示 ? 1 :0
    private Integer enableStatus;
    //创建时间
    private Date createTime;
    //更新时间
    private Date lastEditTime;
}