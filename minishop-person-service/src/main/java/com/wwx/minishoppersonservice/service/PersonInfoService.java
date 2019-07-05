package com.wwx.minishoppersonservice.service;

import com.wwx.minishop.entity.PersonInfo;

public interface PersonInfoService {

    PersonInfo queryPersonInfoWithName(String username);

    Boolean insertPersonInfo(PersonInfo personInfo);

    Boolean updatePersonInfo(PersonInfo personInfo);

    PersonInfo queryPersonInfoWithUserId(Integer userId);
}
