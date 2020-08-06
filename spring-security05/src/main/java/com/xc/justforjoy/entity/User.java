package com.xc.justforjoy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author lxcecho
 * @since 2020-08-06
 */
@TableName("jd_user")
@Data
public class User {

    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer id;

    private String username;

    private String password;

    private String role;

}
