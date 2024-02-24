package com.practiceproject.EmployeeManagementSystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@Order(2)
public class UserSecurityConfig extends WebSecurityConfigurerAdapter{

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/userpage")
            .authenticated()
            .anyRequest()
            .permitAll()
        .and()
        .antMatcher("/userpage").authorizeRequests()
            .anyRequest().hasAnyAuthority("USER")
        .and()
        .formLogin()
            .loginPage("/login")
            // .loginProcessingUrl("/login")
            // .defaultSuccessUrl("/userpage")
            .permitAll()
        .and()
        .logout()
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/login?logout")
            .permitAll();
    }
    
}
