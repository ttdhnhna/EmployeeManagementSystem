package com.practiceproject.EmployeeManagementSystem.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@Order(2)
@EnableWebSecurity
public class EmployeeSecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/employee/**")
            .authorizeRequests().anyRequest().hasAuthority("EMPLOYEE")
            .and()
            .formLogin()
                .loginPage("/employee/login")
                .usernameParameter("email")
                .loginProcessingUrl("/employee/login")
                .defaultSuccessUrl("/employee/home")
                .permitAll()
            .and()
            .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/employee/logout"))
                .permitAll()
            .and()
            .rememberMe()
                .key("AbcdefghijKlmno1234567890")
                .tokenValiditySeconds(24 * 60 * 60);
    }
}
