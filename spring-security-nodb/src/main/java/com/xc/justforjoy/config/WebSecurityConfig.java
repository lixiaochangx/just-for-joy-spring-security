package com.xc.justforjoy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @author lxcecho
 * @since 2020/8/5
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 通常，子类不能通过调用super来调用此方法，一位内他可能会被覆盖其配置，默认配置为：
     * http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
     * <p>
     * 匹配 "/" 路径，不需要权限即可访问
     * 匹配 "/user" 及其以下所有路径，都需要 "USER" 权限
     * 登录地址为 "/login"，登录成功默认跳转到页面 "/user"
     * 退出登录的地址为 "/logout"，退出成功后跳转到页面 "/login"
     * 默认启用 CSRF
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/user/**").hasRole("USER")
                /*and 方法表示结束当前标签，上下文回到HttpSecurity，开启新一轮的配置。*/
                .and()
                /*默认情况下，formPage中的参数和login的action名称是一样的。
                 * 即：当我们定义了登录页面为 /login.html 的时候，Spring Security 也会帮我们自动注册一个
                 * /login.html 的接口，这个接口是 POST 请求，用来处理登录逻辑。*/

                /*不管从哪个页面来的都是跳转到success页面，属于服务端跳转。
                 * 即：不管你是从哪里来的，登录后一律跳转到 successForwardUrl 指定的地址。*/
                /*实际操作中，defaultSuccessUrl 和 successForwardUrl 只需要配置一个即可。*/
                /*.successForwardUrl("/success")*/
                /*重定向*/
                .formLogin().loginPage("/login").defaultSuccessUrl("/user")
                .and()
                /*默认注销的 URL 是 /logout，是一个 GET 请求，我们可以通过 logoutUrl 方法来修改默认的注销 URL。*/
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login");
    }

    /**
     * 在内存中创建一个名为 "lxcecho" 的用户，密码为 "123456"，拥有 "USER" 权限，直接使用，
     * 一定要记得使用 @Bean 将其注入到容器中！！！
     */
    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        // 这里已经使用默认的 PasswordEncoder
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("lxcecho").password("123456").roles("USER").build());
        return manager;

        // 或者
        /*User.UserBuilder users = User.withDefaultPasswordEncoder();
        UserDetails user = users
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        UserDetails admin = users
                .username("admin")
                .password("password")
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);*/

    }


    /**
     * 不加密（已经弃用），一般情况下开发中使用 BCryptPasswordEncoder，
     * 联合 configure(AuthenticationManagerBuilder auth) 使用
     *
     * @return
     */
    /*@Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }*/

    /**
     * 或者在configure(AuthenticationManagerBuilder auth)中在内存中创建一个名为 "lxcecho" 的用户，密码为 "123456"，拥有 "USER" 权限，
     * 不同在于 需要创建一个 PasswordEncoder 加密的实例，否则会报：java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"
     * <p>
     * AuthenticationManagerBuilder 用于创建一个 AuthenticationManager，让我能够轻松的实现内存验证、LADP验证、基于JDBC的验证、添加UserDetailsService、添加AuthenticationProvider。
     *
     * @param auth
     * @throws Exception
     */
    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        *//**
         * 开启在内存中定义用户，withUser 中是用户名，password 中则是用户密码，roles 中是用户角色。
         * 如果需要配置多个用户，用 and 相连。
         * and 表示结束当前标签，这是个时候上下文会重新回到 inMemoryAuthentication 方法中，然后开启新用户的配置。
         *//*
        auth
                .inMemoryAuthentication()
                .withUser("lxcecho")
                .password("123456")
                .roles("USER");
    }*/

    @Override
    public void configure(WebSecurity web) throws Exception {
        /**
         * 不拦截下列的静态资源
         * 配置忽略掉的 URL 地址，一般对于静态文件
         */
        web
                .ignoring()
                .antMatchers()
                .antMatchers("/css/**", "/js/**", "/images/**");
    }

}
