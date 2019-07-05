package com.wwx.minishoppersonservice.dao;

import com.wwx.minishop.entity.LocalAuth;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LocalAuthMapper {

    LocalAuth queryLocalAuthWithName(String username);

    int insertLocalAuth(LocalAuth localAuth);
}
