package com.xc.justforjoy.entity;

import lombok.Data;

/**
 * @author lxcecho
 * @since 2020-08-06
 */
@Data
public class LoginUser {

    private String username;

    private String password;

    private Integer rememberMe;

}
