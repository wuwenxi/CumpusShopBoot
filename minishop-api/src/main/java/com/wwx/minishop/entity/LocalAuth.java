package com.wwx.minishop.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 *   本地账号
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LocalAuth implements Serializable {
    //本地账号编号
    private Integer localAuthId;
    //关联用户
    private PersonInfo personInfo;
    //用户名
    private String userName;
    //用户密码
    private String password;
    //创建时间
    private Date createTime;
    //更新时间
    private Date lastEditTime;
}