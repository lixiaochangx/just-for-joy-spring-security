package com.xc.justforjoy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xc.justforjoy.entity.BackUser;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lxcecho
 * @since 2020-08-05
 */
public interface BackUserService extends IService<BackUser> {

    /**
     * userName:唯一， 默认 USER 权限
     *
     * @param backUser
     */
    void insert(BackUser backUser);

    /**
     * 根据userName查询用户信息
     *
     * @param username
     * @return
     */
    BackUser getByUsername(String username);

}
