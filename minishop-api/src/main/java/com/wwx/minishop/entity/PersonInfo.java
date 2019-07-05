package com.wwx.minishop.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 *    用户信息，包括普通用户、店家、超级管理员的信息
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PersonInfo implements Serializable {
    //编号
    private Integer userId;
    //用户真实姓名
    private String name;
    //性别
    private String gender;
    //电子邮箱
    private String email;
    //用户头像地址
    private String profileImg;
    //用户类型  普通用户  店家  超级管理员
    private Integer userType;
    //创建时间
    private Date createTime;
    //更新时间
    private Date lastEditTime;
    //用户状态 禁止访问：可以访问？0:1
    private Integer enableStatus;
    //用户名
    private String userName;
    //密码
    private String password;

    private String phone;
}