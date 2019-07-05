package com.wwx.minishopshopmanager.security;

import com.wwx.minishop.entity.LocalAuth;
import com.wwx.minishopshopmanager.controller.PersonInfoController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;


@Component
public class LocalAuthDetailService implements UserDetailsService {

    //密码验证加密
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        if(userName ==null){
            throw new UsernameNotFoundException("用户名为空");
        }else {
            LocalAuth localAuth = restTemplate.getForObject(PersonInfoController.REST_URL_PERSON_PREFIX+"/findLocalAuthWithName/"+userName,LocalAuth.class);
            if(localAuth!=null){
                String password = passwordEncoder.encode(localAuth.getPassword());
                request.getSession().setAttribute("localAuth",localAuth);
                String authority = null;
                if(localAuth.getPersonInfo().getUserType()!=null){
                    switch (localAuth.getPersonInfo().getUserType()){
                        case 1:
                            authority = "shopAdmin";
                            break;
                    }
                }
                return new User(userName,password,
                        AuthorityUtils.commaSeparatedStringToAuthorityList(authority));
            }else {
                throw new UsernameNotFoundException("不存在当前用户");
            }
        }
    }
}
