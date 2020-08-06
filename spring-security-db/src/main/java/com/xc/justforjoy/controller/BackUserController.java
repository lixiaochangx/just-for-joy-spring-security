package com.xc.justforjoy.controller;


import com.xc.justforjoy.entity.BackUser;
import com.xc.justforjoy.service.BackUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lxcecho
 * @since 2020-08-05
 */
@Controller
public class BackUserController {

    @Autowired
    private BackUserService backUserService;

    @GetMapping({"/", "/index", "/home"})
    public String root(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(BackUser backUser){
        // 此处省略校验逻辑
        backUserService.insert(backUser);
        return "redirect:register?success";
    }

    @GetMapping("/user")
    public String user(@AuthenticationPrincipal Principal principal, Model model){
        model.addAttribute("username", principal.getName());
        return "user/user";
    }

}
