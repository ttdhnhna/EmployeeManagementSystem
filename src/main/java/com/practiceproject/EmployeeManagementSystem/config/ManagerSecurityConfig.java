package com.practiceproject.EmployeeManagementSystem.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.practiceproject.EmployeeManagementSystem.service.CustomLogoutSuccessHandler;
import com.practiceproject.EmployeeManagementSystem.service.CustomUserDetailsService;

@Configuration
@Order(1)
public class ManagerSecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
    DataSource dataSource;
    @Autowired
    private CustomLogoutSuccessHandler logout;

    @Bean
    public UserDetailsService userDetailsService(){
        return new CustomUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/").hasAuthority("MANAGER")
            .antMatchers("/manager/**").hasAuthority("MANAGER")
            .antMatchers("/registration", "/styles/**", "/image/**", "/forgotpassword"
                , "/employee/login", "/saveRegistration", "/upforgotpassword", "/resetpassword"
                , "/upresetpassword"
            ).permitAll()
            // .antMatchers("/forgotpassword").permitAll()
            // .antMatchers("/employee/login").permitAll()
            // .antMatchers("/manager/login/?lang=en").permitAll()
            // .antMatchers("/manager/login/?lang=vi").permitAll()
            // .antMatchers("/forgotpassword/?lang=en").permitAll()
            // .antMatchers("/forgotpassword/?lang=vi").permitAll()
            // .antMatchers("/registration/?lang=en").permitAll()
            // .antMatchers("/registration/?lang=vi").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
                .loginPage("/manager/login")
                .usernameParameter("email")
                .loginProcessingUrl("/manager/login")
                .defaultSuccessUrl("/")
                .permitAll()
            .and()
            .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/manager/logout"))
                .logoutSuccessHandler(logout)
                .permitAll()
            .and()
            .rememberMe()
                .key("AbcdefghijKlmno1234567890")
                .tokenValiditySeconds(24 * 60 * 60);
    }

    
}
