package com.wwx.minishoppersonservice.dao;

import com.wwx.minishop.entity.PersonInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PersonInfoMapper {

    PersonInfo queryPersonInfoWithName(String name);

    boolean insertPersonInfo(PersonInfo personInfo);

    boolean updatePersonInfo(PersonInfo personInfo);

    PersonInfo queryPersonInfoWithUserId(Integer userId);
}
