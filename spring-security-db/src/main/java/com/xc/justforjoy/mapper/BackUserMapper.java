package com.xc.justforjoy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xc.justforjoy.entity.BackUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lxcecho
 * @since 2020-08-05
 */
@Repository
@Mapper
public interface BackUserMapper extends BaseMapper<BackUser> {

    /**
     * 根据账户查询用户信息
     *
     * @param username
     * @return
     */
    BackUser findByUsername(String username);

}
