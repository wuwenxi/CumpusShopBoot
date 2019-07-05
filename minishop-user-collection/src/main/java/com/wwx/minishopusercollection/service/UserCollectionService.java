package com.wwx.minishopusercollection.service;

import com.wwx.minishop.entity.PersonInfo;
import com.wwx.minishop.entity.UserCollection;

import java.util.List;

public interface UserCollectionService {

    int insertUserCollection(UserCollection userCollection);

    List<UserCollection> queryUserCollectionList(PersonInfo personInfo);

    int deleteUserCollection(UserCollection userCollection);

    UserCollection queryUserCollectionWithPersonAndShop(UserCollection userCollection);
}
