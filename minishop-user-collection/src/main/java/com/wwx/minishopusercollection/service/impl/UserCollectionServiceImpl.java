package com.wwx.minishopusercollection.service.impl;

import com.wwx.minishop.entity.PersonInfo;
import com.wwx.minishop.entity.UserCollection;
import com.wwx.minishopusercollection.dao.UserCollectionMapper;
import com.wwx.minishopusercollection.service.UserCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@CacheConfig(cacheManager = "cacheManager")
@Service
public class UserCollectionServiceImpl implements UserCollectionService {

    @Autowired
    UserCollectionMapper userCollectionMapper;

    @CacheEvict(cacheNames = "userCollectionList",key = "'personInfo'+#userCollection.personInfo.userId")
    @Override
    public int insertUserCollection(UserCollection userCollection) {
        userCollection.setCreateTime(new Date());
        return userCollectionMapper.insertUserCollection(userCollection);
    }

    @Cacheable(cacheNames = "userCollectionList",key = "'personInfo'+#personInfo.userId",unless = "#result==null")
    @Override
    public List<UserCollection> queryUserCollectionList(PersonInfo personInfo) {
        return userCollectionMapper.queryUserCollectionList(personInfo);
    }

    @CacheEvict(cacheNames = "userCollectionList",key = "'personInfo'+#userCollection.personInfo.userId")
    @Override
    public int deleteUserCollection(UserCollection userCollection) {
        return userCollectionMapper.deleteUserCollection(userCollection);
    }

    @Cacheable(cacheNames = "userCollection",
            key = "'userCollection'+#userCollection.personInfo.userId+'and'+#userCollection.shop.shopId")
    @Override
    public UserCollection queryUserCollectionWithPersonAndShop(UserCollection userCollection) {
        return userCollectionMapper.queryUserCollectionWithPersonAndShop(userCollection);
    }
}
