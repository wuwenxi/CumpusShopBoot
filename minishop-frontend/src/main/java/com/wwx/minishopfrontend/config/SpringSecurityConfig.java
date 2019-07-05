package com.wwx.minishopfrontend.config;

import com.wwx.minishopfrontend.security.PersonInfoDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    PersonInfoDetailService personInfoDetailService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/userLogin")
                .failureUrl("/userLogin?error=true")
                .defaultSuccessUrl("/")//跳转指定页面
                .and()
                .authorizeRequests()
                //过滤静态文件
                .antMatchers("/css/**","/image/**","/js/**","/upload/**").permitAll()
                .antMatchers("/","/frontend/**","/userLogin","/register","/personInfo/**").permitAll()
                .antMatchers("/userCollectionLogin","/order").hasAnyAuthority("user","shopAdmin")
                .anyRequest().authenticated();

        http.logout()
                .permitAll().
                invalidateHttpSession(true).
                deleteCookies("remember-me")
                .logoutSuccessHandler(logoutSuccessHandler())
                .and()
                .sessionManagement()
                .maximumSessions(10)
                .expiredUrl("/userLogin");
        //
        http.rememberMe()
                .rememberMeParameter("remember-me")
                .tokenRepository(tokenRepository())
                .userDetailsService(personInfoDetailService);

        http.csrf().disable();
    }

    /**
     *
     *           过滤静态文件
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**");
    }

    @Autowired
    private DataSource dataSource;

    @Bean
    public JdbcTokenRepositoryImpl tokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() { //登出处理
        return (httpServletRequest, httpServletResponse, authentication) -> {
            try {
                String personName = authentication.getName();
                System.out.println("USER : " + personName + " LOGOUT SUCCESS !  ");
            } catch (Exception e) {
                System.out.println("LOGOUT EXCEPTION , e : " + e.getMessage());
            }
            httpServletResponse.sendRedirect("/");
        };
    }


}
