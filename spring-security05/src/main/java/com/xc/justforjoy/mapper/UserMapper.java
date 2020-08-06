package com.xc.justforjoy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xc.justforjoy.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author lxcecho
 * @since 2020-08-06
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * findByUsername
     *
     * @param username
     * @return
     */
    User findByUsername(String username);
}
