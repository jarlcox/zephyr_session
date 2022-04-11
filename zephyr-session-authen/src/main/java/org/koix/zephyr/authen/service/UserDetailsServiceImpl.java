package org.koix.zephyr.authen.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * 授权的时候是对角色授权，而认证的时候应该基于资源，而不是角色，因为资源是不变的，而用户的角色是会变的
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if("admin".equals(username))
        {
            authorities.add(new SimpleGrantedAuthority("memberExport"));
            authorities.add(new SimpleGrantedAuthority("BookList"));
        }
        else if("user".equals(username))
        {
            authorities.add(new SimpleGrantedAuthority("memberExport"));
        }
        else
            throw new UsernameNotFoundException(username);
        //123456
        String password="$2a$10$aZDOWYEvK06TrxN6g0Mta.X3gtnj1sHPReRic5YRcOiXl4yMctwS6";
        return new User(username, password, authorities);
    }
}
