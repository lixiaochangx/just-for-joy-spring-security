package com.xc.justforjoy.config;

import com.xc.justforjoy.entity.BackUser;
import com.xc.justforjoy.service.BackUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lxcecho
 * @since 2020/8/5
 *
 * 自定义校验
 */
@Service
public class DbUserDetailsService implements UserDetailsService {

    @Autowired
    private BackUserService backUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        BackUser backUser = backUserService.getByUsername(username);

        if (backUser == null) {
            throw new UsernameNotFoundException("用户不存在！");
        }

        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority("USER"));
        return new User(backUser.getUsername(), backUser.getPassword(), simpleGrantedAuthorities);
    }

}
