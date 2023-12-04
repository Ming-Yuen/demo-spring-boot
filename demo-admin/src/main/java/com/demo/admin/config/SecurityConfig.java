package com.demo.admin.config;

import com.demo.admin.filter.JwtAuthenticationTokenFilter;
import com.demo.admin.filter.RestAuthenticationEntryPoint;
import com.demo.admin.filter.RestfulAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
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
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
        registry.antMatchers(HttpMethod.GET).permitAll();
        for(String path : ignoreList){
            registry.antMatchers(path).permitAll();
        }
        registry.antMatchers("/actuator/**")
//                .requestMatchers(EndpointRequest.toAnyEndpoint())
                .hasRole("ADMIN")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll();

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
