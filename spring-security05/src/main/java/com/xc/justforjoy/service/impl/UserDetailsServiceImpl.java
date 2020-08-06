package com.xc.justforjoy.service.impl;

import com.xc.justforjoy.entity.JwtUser;
import com.xc.justforjoy.entity.User;
import com.xc.justforjoy.mapper.UserMapper;
import com.xc.justforjoy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lxcecho
 * @since 2020-08-06
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class UserDetailsServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userMapper.findByUsername(s);
        return new JwtUser(user);
    }

}

