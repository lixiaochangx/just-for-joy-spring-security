package com.xc.justforjoy.controller;

import com.xc.justforjoy.result.Result;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lxcecho
 * @since 2020-08-05
 */
@RestController
public class BackUserController {

    @PostMapping("/user")
    public Result user(@AuthenticationPrincipal Principal principal){
        return Result.success(principal.getName());
    }

    @PostMapping("/admin")
    public Result admin(@AuthenticationPrincipal Principal principal){
        return Result.success(principal.getName());
    }


}
