package com.xc.justforjoy.controller;


import com.xc.justforjoy.entity.BackUser;
import com.xc.justforjoy.result.Result;
import com.xc.justforjoy.service.BackUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lxcecho
 * @since 2020-08-05
 */
@RestController
public class HomeController {

    @Autowired
    private BackUserService backUserService;

    @PostMapping("/register")
    public Result doRegister(@RequestBody BackUser backUser) {
        // 此处省略校验逻辑
        backUserService.insert(backUser);
        return Result.success("注册成功");
    }

}
