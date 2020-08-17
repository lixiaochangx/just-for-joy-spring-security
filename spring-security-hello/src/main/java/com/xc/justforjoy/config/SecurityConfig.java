package com.xc.justforjoy.config;

import com.xc.justforjoy.repository.UserDetailsRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

/**
 * @author lxcecho
 * @since 2020/8/4
 * <p>
 * SecurityConfig配置文件
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // TODO 自定义UserDetailsManager 待检测结果是否能运行
    /**
     * 将UserDetailsRepository注入spring容器
     *
     * @return
     */
    /*@Bean
    public UserDetailsRepository userDetailsRepository() {
        UserDetailsRepository userDetailsRepository = new UserDetailsRepository();
        // 为了让我们的登录能够运行 这里我们初始化一个用户lxcecho 密码采用明文 当你在密码12345上使用了前缀{noop} 意味着你的密码不使用加密，
        // authorities 一定不能为空 这代表用户的角色权限集合
        UserDetails lxcecho = User
                .withUsername("lxcechoo")
                .password("{noop}123456")
                .authorities(AuthorityUtils.NO_AUTHORITIES)
                .build();
        userDetailsRepository.createUser(lxcecho);
        return userDetailsRepository;
    }

    @Bean
    public UserDetailsManager userDetailsManager(UserDetailsRepository userDetailsRepository) {
        return new UserDetailsManager() {
            @Override
            public void createUser(UserDetails userDetails) {
                userDetailsRepository.createUser(userDetails);
            }

            @Override
            public void updateUser(UserDetails userDetails) {
                userDetailsRepository.updateUser(userDetails);
            }

            @Override
            public void deleteUser(String s) {
                userDetailsRepository.deleteUser(s);
            }

            @Override
            public void changePassword(String s, String s1) {
                userDetailsRepository.changePassword(s, s1);
            }

            @Override
            public boolean userExists(String s) {
                return userDetailsRepository.userExists(s);
            }

            @Override
            public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
                return userDetailsRepository.loadUserByUsername(s);
            }
        };
    }*/

    // @formatter:off

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorize -> authorize
                .antMatchers("/css/**", "/index").permitAll()
                .antMatchers("/user/**").hasRole("USER"))
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .failureUrl("/login-error"));
    }

    // @formatter:on

    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        // 使用默认PasswordEncoder
        UserDetails userDetails = User.withDefaultPasswordEncoder()
                .username("lxcecho")
                .password("123456")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(userDetails);

        // 使用BCryptPasswordEncoder加密密码
       /* UserDetails user = User.builder()
                .username("user")
                .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);*/
    }

}

