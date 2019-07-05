package com.wwx.minishoppersonservice.service.impl;

import com.wwx.minishop.entity.PersonInfo;
import com.wwx.minishoppersonservice.dao.PersonInfoMapper;
import com.wwx.minishoppersonservice.service.PersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.Date;

@CacheConfig(cacheManager = "cacheManager")
@Service
public class PersonInfoServiceImpl implements PersonInfoService {

    @Autowired
    PersonInfoMapper personInfoMapper;

    @Cacheable(cacheNames = "personInfo",key = "#username",unless = "#result == null")
    @Override
    public PersonInfo queryPersonInfoWithName(String username) {
        return personInfoMapper.queryPersonInfoWithName(username);
    }

    @Override
    public Boolean insertPersonInfo(PersonInfo personInfo) {
        personInfo.setCreateTime(new Date());
        personInfo.setEnableStatus(0);
        personInfo.setLastEditTime(new Date());
        personInfo.setUserType(0);
        return personInfoMapper.insertPersonInfo(personInfo);
    }


    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "personInfo",allEntries = true)
            },
            cacheable = {
                    @Cacheable(cacheNames = "personInfo",key = "#personInfo.userName")
            }
    )
    @Override
    public Boolean updatePersonInfo(PersonInfo personInfo) {
        personInfo.setLastEditTime(new Date());
        return personInfoMapper.updatePersonInfo(personInfo);
    }

    @Cacheable(cacheNames = "userId",key = "'userId'+#userId",unless = "#result==null")
    @Override
    public PersonInfo queryPersonInfoWithUserId(Integer userId) {
        return personInfoMapper.queryPersonInfoWithUserId(userId);
    }
}
