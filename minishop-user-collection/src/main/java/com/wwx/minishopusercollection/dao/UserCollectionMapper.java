package com.wwx.minishopusercollection.dao;

import com.wwx.minishop.entity.PersonInfo;
import com.wwx.minishop.entity.UserCollection;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserCollectionMapper {

    int insertUserCollection(UserCollection userCollection);

    List<UserCollection> queryUserCollectionList(PersonInfo personInfo);

    int deleteUserCollection(UserCollection userCollection);

    UserCollection queryUserCollectionWithPersonAndShop(UserCollection userCollection);
}
