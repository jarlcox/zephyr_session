package org.koix.zephyr.authen.config;

import org.koix.zephyr.authen.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationProviderImpl implements AuthenticationProvider
{
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException
    {
        if(auth==null || auth.getName()==null || auth.getCredentials()==null)
            throw new BadCredentialsException("Login params is null!");

        //读取用户信息
        String username=auth.getName();
        String password=auth.getCredentials().toString();

        //读取数据库内用户信息
        UserDetails udata=this.userDetailsService.loadUserByUsername(username);
        if(udata==null)
            throw new BadCredentialsException("User not found!");

        //验证密码
        if(!this.passwordEncoder.matches(password, udata.getPassword()))
            throw new BadCredentialsException("Invalid password!");

        //返回token数据
        UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(username, password, udata.getAuthorities());
        //details
        authentication.setDetails("ddd");
        System.out.println("--------------->");
        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication)
    {
        //检验token类型
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
