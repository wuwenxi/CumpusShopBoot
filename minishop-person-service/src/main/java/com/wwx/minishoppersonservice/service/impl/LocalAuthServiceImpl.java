package com.wwx.minishoppersonservice.service.impl;

import com.wwx.minishop.entity.LocalAuth;
import com.wwx.minishoppersonservice.dao.LocalAuthMapper;
import com.wwx.minishoppersonservice.service.LocalAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;

@CacheConfig(cacheManager = "cacheManager")
@Service
public class LocalAuthServiceImpl implements LocalAuthService {

    @Autowired
    LocalAuthMapper localAuthMapper;

    @Cacheable(cacheNames = "localAuth",key = "#username",unless = "#result == null")
    @Override
    public LocalAuth queryLocalAuthWithName(String username) {
        return localAuthMapper.queryLocalAuthWithName(username);
    }

    @Override
    public int insertLocalAuth(LocalAuth localAuth) {
        localAuth.setCreateTime(new Date());
        localAuth.setLastEditTime(new Date());
        return localAuthMapper.insertLocalAuth(localAuth);
    }
}
