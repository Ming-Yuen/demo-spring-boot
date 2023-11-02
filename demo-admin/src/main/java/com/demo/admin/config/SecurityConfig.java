package com.demo.admin.config;

import com.demo.admin.dao.UserInfoDao;
import com.demo.admin.dto.AdminUserDetails;
import com.demo.admin.filter.JwtAuthenticationTokenFilter;
import com.demo.admin.filter.RestAuthenticationEntryPoint;
import com.demo.admin.filter.RestfulAccessDeniedHandler;
import com.demo.common.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@Order(3)
public class SecurityConfig {
    @Value("${jwt.ignore}")
    private List<String> ignoreList;
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    @Autowired
    private UserInfoDao userDao;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
        for(String path : ignoreList){
            registry.antMatchers(path).permitAll();
        }

        return registry.anyRequest().authenticated()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
//                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and().addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
//        for(String path : ignoreList){
//            registry.antMatchers(path).permitAll();
//        }
//
//        registry.anyRequest()
//                .authenticated()
//                // 关闭跨站请求防护及不使用session
//                .and()
//                .csrf()
//                .disable()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                // 自定义权限拒绝处理类
//                .and()
//                .exceptionHandling()
//                .accessDeniedHandler(restfulAccessDeniedHandler)
////                .authenticationEntryPoint(restAuthenticationEntryPoint)
//                // 自定义权限拦截器JWT过滤器
//                .and()
//                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
//    }
//
//    //    @Override
////    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
////        auth.inMemoryAuthentication().withUser("admin").password("123456").roles("admin");
////        // 添加userAdd账号
////        auth.inMemoryAuthentication().withUser("userAdd").password("123456").authorities("showOrder","addOrder");
////        // 如果想实现动态账号与数据库关联 在该地方改为查询数据库
////    }
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("admin")
//                .password("admin").roles("admin")
//                .and()
//                .withUser("ming")
//                .password("ming")
//                .roles("user");
//    }
//    @Bean
//    RoleHierarchy roleHierarchy() {
//        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
//        hierarchy.setHierarchy("ROLE_admin > ROLE_user");
//        return hierarchy;
//    }

}
