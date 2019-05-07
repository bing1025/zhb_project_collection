package com.us.example.config;

import com.us.example.security.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by yangyibo on 17/1/18.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
     private  CustomUserService customUserService;
    
    /**   https://blog.csdn.net/bryBLOG/article/details/77529619
     * 配置.忽略的静态文件，不加的话，登录之前页面的css,js不能正常使用，得登录之后才能正常.
     */

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 忽略URL
        web.ignoring().antMatchers("/**/*.js", "/lang/*.json", "/**/*.css", "/**/*.js", "/**/*.map", "/**/*.html",
                "/**/*.png");
    }

/* 此处使用了 BCryptPasswordEncoder 密码加密
  https://blog.csdn.net/u012373815/article/details/56832167
    使用了httpBasic 认证 ： springSecurity 之 http Basic认证 （四）
 * (non-Javadoc)
 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder)
 */
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/users/**")
                .authenticated()
                .antMatchers(HttpMethod.POST)
                .authenticated()
                .antMatchers(HttpMethod.PUT)
                .authenticated()
                .antMatchers(HttpMethod.DELETE)
                .authenticated()
                .antMatchers("/**")
                .permitAll()
                .and()
                .sessionManagement()
                .and()
                .httpBasic();
    }



}

