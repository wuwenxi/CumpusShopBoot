package com.wwx.minishoppersonservice.service;

import com.wwx.minishop.entity.LocalAuth;

public interface LocalAuthService {

    LocalAuth queryLocalAuthWithName(String username);

    int insertLocalAuth(LocalAuth localAuth);
}
