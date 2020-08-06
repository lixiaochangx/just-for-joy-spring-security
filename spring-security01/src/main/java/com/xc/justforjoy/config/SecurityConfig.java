package com.xc.justforjoy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author lxcecho
 * @since 2020/8/4
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 代码配置大于XML配置，所以写配置类后前面配置文件的配置会被这里覆盖掉
     */
    /*@Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                /*所有请求都需要认证*/
                .anyRequest().authenticated()
                /*and 方法表示结束当前标签，上下文回到HttpSecurity，开启新一轮的配置。*/
                .and()
                .formLogin()
                /*默认情况下，formPage中的参数和login的action名称是一样的。
                 * 即：当我们定义了登录页面为 /login.html 的时候，Spring Security 也会帮我们自动注册一个
                 * /login.html 的接口，这个接口是 POST 请求，用来处理登录逻辑。*/
                .loginPage("/login.html")
                /*.usernameParameter("name")
                .passwordParameter("passwd")*/
                .loginProcessingUrl("/doLogin")
                /*不管从哪个页面来的都是跳转到success页面，属于服务端跳转。
                 * 即：不管你是从哪里来的，登录后一律跳转到 successForwardUrl 指定的地址。*/
                /*实际操作中，defaultSuccessUrl 和 successForwardUrl 只需要配置一个即可。*/
                /*.successForwardUrl("/success")*/
                /*重定向*/
                .defaultSuccessUrl("/success")
                /*.failureForwardUrl("/error")*/
                .failureUrl("/login.html")
                .permitAll()
                .and()
                /*默认注销的 URL 是 /logout，是一个 GET 请求，我们可以通过 logoutUrl 方法来修改默认的注销 URL。*/
                .logout()
                .logoutUrl("/logout")
                /*logoutRequestMatcher 方法不仅可以修改注销 URL，还可以修改请求方式，实际项目中，这个方法和 logoutUrl 任意设置一个即可。*/
                /*.logoutRequestMatcher(new AntPathRequestMatcher("/logout","POST"))*/
                /*logoutSuccessUrl 表示注销成功后要跳转的页面。*/
                /*.logoutSuccessUrl("/index")*/
                /*deleteCookies 用来清除 cookie。*/
                /*.deleteCookies()*/
                /*clearAuthentication 和 invalidateHttpSession 分别表示清除认证信息
                和使 HttpSession 失效，默认可以不用配置，默认就会清除。*/
                /*.clearAuthentication(true)
                .invalidateHttpSession(true)*/
                .and()
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /**
         * 开启在内存中定义用户，withUser 中是用户名，password 中则是用户密码，roles 中是用户角色。
         * 如果需要配置多个用户，用 and 相连。
         * and 表示结束当前标签，这是个时候上下文会重新回到 inMemoryAuthentication 方法中，然后开启新用户的配置。
         */
        auth.inMemoryAuthentication().withUser("lxcecho").password("123456").roles("admin");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        /**
         * 不拦截下列的静态资源
         * 配置忽略掉的 URL 地址，一般对于静态文件
         */
        web.ignoring().antMatchers().antMatchers("/css/**", "/js/**", "/images/**");
    }
}
