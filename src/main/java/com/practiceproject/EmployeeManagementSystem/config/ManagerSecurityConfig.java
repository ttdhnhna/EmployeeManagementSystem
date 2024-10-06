package com.practiceproject.EmployeeManagementSystem.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.practiceproject.EmployeeManagementSystem.service.CustomLogoutSuccessHandler;
import com.practiceproject.EmployeeManagementSystem.service.CustomUserDetailsService;

@Configuration
@Order(2)
@EnableWebSecurity
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

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider=new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    // Purpose: The DaoAuthenticationProvider is a specific implementation of AuthenticationProvider that uses a data-access object (DAO) to retrieve user information.
    // What it does: This provider takes care of fetching the user details (via UserDetailsService) and validating the password (using BCryptPasswordEncoder).
    // It sets the UserDetailsService (from userDetailsService()) to fetch user details (like username, password, and roles).
    // It sets the PasswordEncoder (from passwordEncoder()) to validate and compare the password.
    // Why it's needed: This is the part of the authentication mechanism where the user’s credentials are verified. It compares the provided credentials (username and password) 
    // with the stored ones (loaded by the CustomUserDetailsService and checked using BCryptPasswordEncoder).

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/").authenticated()
            .antMatchers("/manager/**").hasAuthority("MANAGER")
            .antMatchers("/registration", "/styles/**", "/image/**", "/forgotpassword"
                , "/employee/login", "/saveRegistration", "/upforgotpassword", "/resetpassword"
                , "/upresetpassword", "/manager/login/?lang=en", "/manager/login/?lang=vi"
                , "/forgotpassword/?lang=en", "/forgotpassword/?lang=vi", "/registration/?lang=en"
                , "/registration/?lang=vi"
            ).permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
                .loginPage("/manager/login")
                .usernameParameter("username")
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
