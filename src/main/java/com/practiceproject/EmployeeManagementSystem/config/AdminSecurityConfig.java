package com.practiceproject.EmployeeManagementSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@Order(2)
public class AdminSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain2(HttpSecurity http) throws Exception{
        http.antMatcher("/loginadmin")
            .authorizeRequests().anyRequest()
            .hasAuthority("ADMIN")
            .and()
            .formLogin()
                .loginPage("/loginadmin")
                .usernameParameter("username")
                .loginProcessingUrl("/loginadmin")
                .defaultSuccessUrl("/")
                .permitAll()
            .and()
            .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/loginadmin?logout")
                .permitAll();
        return http.build();
    }
}
