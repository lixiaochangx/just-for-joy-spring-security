package com.xc.justforjoy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xc.justforjoy.entity.BackUser;
import com.xc.justforjoy.mapper.BackUserMapper;
import com.xc.justforjoy.service.BackUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lxcecho
 * @since 2020-08-05
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class BackUserServiceImpl extends ServiceImpl<BackUserMapper, BackUser> implements BackUserService {

    private final static Map<Integer, String> ENCODER_TYPE = new HashMap<>();

    private final static Map<String, PasswordEncoder> ENCODER_MAP = new HashMap<>();

    private final static String PASSWORD_FORMAT = "{%s}%s";

    @Autowired
    private BackUserMapper backUserMapper;

    static {
        ENCODER_TYPE.put(0, "noop");
        ENCODER_TYPE.put(1, "bcrypt");
        ENCODER_TYPE.put(2, "pbkdf2");
        ENCODER_TYPE.put(3, "scrypt");
        ENCODER_TYPE.put(4, "sha256");
        ENCODER_MAP.put("noop", NoOpPasswordEncoder.getInstance());
        ENCODER_MAP.put("bcrypt", new BCryptPasswordEncoder());
        ENCODER_MAP.put("pbkdf2", new Pbkdf2PasswordEncoder());
        ENCODER_MAP.put("scrypt", new SCryptPasswordEncoder());
        ENCODER_MAP.put("sha256", new StandardPasswordEncoder());
    }

    @Override
    public void insert(BackUser backUser) {
        String userName = backUser.getUsername();
        if (exist(userName)) {
            throw new RuntimeException("用户名已存在！");
        }
        // 随机使用加密方式
        Random random = new Random();
        int x = random.nextInt(5);
        String encoderType = ENCODER_TYPE.get(x);
        PasswordEncoder passwordEncoder = ENCODER_MAP.get(encoderType);
        // todo 登录的时候就得使用加密过的密码进行登录？ 即得去数据库中拿到一长串的密码
        backUser.setPassword(String.format(PASSWORD_FORMAT, encoderType, passwordEncoder.encode(backUser.getPassword())));
        backUserMapper.insert(backUser);
    }

    @Override
    public BackUser getByUsername(String username) {
        return backUserMapper.findByUsername(username);
    }

    /**
     * 判断用户是否存在
     */
    private boolean exist(String username) {
        BackUser backUser = backUserMapper.findByUsername(username);
        return (backUser != null);
    }

}
