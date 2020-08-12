package com.xc.justforjoy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class SpringSecurityHelloApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    public void accessUnprotected() throws Exception {
        this.mockMvc
                .perform(get("/index"))
                .andExpect(status().isOk());
    }


    /**
     * SecurityContextHolder ：
     * 默认情况下，SecurityContext使用ThreadLocal来存储这些细节，这意味着SecurityContext始终对执行的同一线程中的方法可用，
     * 即使SecurityContext没有显式地作为参数传递给这些方法。如果在当前主体的请求被处理后清除线程，那么以这种方式使用ThreadLocal是非常安全的。
     * Spring Security的FilterChainProxy确保SecurityContext总是被清除。
     * <p>
     * 有些应用程序并不完全适合使用ThreadLocal，这是因为它们使用线程的特定方式。例如，Swing客户机可能希望Java虚拟机中的所有线程使用相同的安全上下文。
     * securitycontext tholder可以在启动时配置一个策略，以指定您希望如何存储上下文。对于独立应用程序，您将使用securitycontexts tholder。MODE_GLOBAL策略。
     * 其他应用程序可能希望由安全线程派生的线程也采用相同的安全标识。这是通过使用securitycontext . mode_inheritablethreadlocal实现的。您可以从默认的securitycontext更改模式。
     * MODE_THREADLOCAL有两种方式。第一个是设置一个系统属性，第二个是调用securitycontext的静态方法。
     * 大多数应用程序不需要更改默认值，但如果要更改，请查看securitycontext的JavaDoc以了解更多信息。
     * <p>
     * Authentication 在 Springsecurity 中有两个用途：
     * 1.作为AuthenticationManager的输入，用于提供用户为进行身份验证而提供的凭据。在此场景中使用isAuthenticated()时，返回false。
     * 2.表示当前经过身份验证的用户。当前的身份验证可以从SecurityContext获得。
     * Authentication 包含以下信息：
     * 1.principle：通常是个用户名，识别用户。当使用用户名/密码进行身份验证时，这通常是UserDetails的实例。
     * 2.credentials：通常是一个密码。在许多情况下，这将在用户经过身份验证后被清除，以确保它不会泄漏。
     * 3.authorities：GrantedAuthority 是授予用户的高级权限。一些例子是 角色 或 作用域。
     * <p>
     * GrantedAuthority ：
     * 被授予的权限可以从Authentication.getAuthorities()获取，返回的是一个一个GrantedAuthority对象的集合。授予的权力是授予主体的权力，
     * 这并不奇怪。这样的权限通常是“角色”，比如ROLE_ADMINISTRATOR或ROLE_HR_SUPERVISOR。这些角色稍后将配置为web授权、方法授权和域对象授权。
     * Spring Security的其他部分能够解释这些权限，并期望它们出现。当使用基于用户名/密码的身份验证时，授权权限通常由UserDetailsService加载。
     * 通常，GrantedAuthority对象默认是是application-wide域范围的权限。它们不是特定于给定域对象的。
     * <p>
     * AuthenticationManager ：
     * AuthenticationManager是定义Spring安全过滤器如何执行身份验证的API。然后，调用AuthenticationManager的控制器(即Spring Security的过滤器)
     * 在securitycontext上设置返回的身份验证。如果您没有与Spring Security的过滤器集成，您可以直接设置SecurityContextHolder，并且不需要使用AuthenticationManager。
     * 虽然AuthenticationManager的实现可以是任何东西，但最常见的实现是 ProviderManager。
     * <p>
     * ProviderManager
     */

    private static AuthenticationManager am = new SampleAuthenticationManager();

    @Test
    public void testSecurityContextHolder() {
        String username = "lxcecho";
        String password = "123456";
        try {
            // 创建一个空的 SecurityContext，不使用SecurityContextHolder.getContext().setAuthentication(authentication);是为了避免多线程之间的竞争。
            SecurityContext context = SecurityContextHolder.createEmptyContext();

            //Spring Security并不关心在SecurityContext上设置什么类型的Authentication实现。这里使用TestingAuthenticationToken，因为简单。
            TestingAuthenticationToken authentication = new TestingAuthenticationToken(username, password, "ROLE_USER");
            // 但是一般我们开发中使用 UsernamePasswordAuthenticationToken(userDetails, password, authorities)
            // Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, password, authorities);

            // 不参与AuthenticationManager管理（即过滤器集成）的话就可以直接使用SecurityContextHolder
            am.authenticate(authentication);

            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException(e.getMessage());
        }

        // 如果希望获取有关已验证的主体的信息，可以通过访问SecurityContextHolder来获得。
        SecurityContext context = SecurityContextHolder.getContext();
        // 获取 Authentication 认证信息
        Authentication authentication = context.getAuthentication();
        // 获取 GrantedAuthority 被授予的权限信息
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        System.out.println("Successfully authenticated. Security context contains: " + authentication);
    }

}

class SampleAuthenticationManager implements AuthenticationManager {

    static final List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();

    static {
        AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        if (auth.getName().equals(auth.getPrincipal())) {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(auth.getName(),
                    auth.getCredentials(), AUTHORITIES);
            return token;
        }
        throw new BadCredentialsException("Bad Credentials");
    }
}
