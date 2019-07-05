package com.wwx.minishopfrontend.security;

import com.wwx.minishop.entity.PersonInfo;
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
public class PersonInfoDetailService implements UserDetailsService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        if(userName ==null){
            throw new UsernameNotFoundException("用户名为空");
        }else {
            PersonInfo personInfo = restTemplate.getForObject("http://MINISHOP-PERSON-SERVICE/personInfo/findPersonInfoWithName/"+userName,PersonInfo.class);
            if(personInfo!=null){
                String password = passwordEncoder.encode(personInfo.getPassword());
                request.getSession().setAttribute("personInfo",personInfo);
                String authority = null;
                if(personInfo.getUserType()!=null){
                    switch (personInfo.getUserType()){
                        case 0:
                            authority = "user";
                            break;
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
