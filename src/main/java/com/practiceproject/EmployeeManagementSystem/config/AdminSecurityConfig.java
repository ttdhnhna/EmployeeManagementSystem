package com.practiceproject.EmployeeManagementSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.practiceproject.EmployeeManagementSystem.service.CustomUserDetailsService;

@Configuration
@Order(1)
public class AdminSecurityConfig extends WebSecurityConfigurerAdapter{
    @Bean
    public UserDetailsService userDetailsService(){
        return new CustomUserDetailsService();
   }
   
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    
    @Override 
    protected void configure(HttpSecurity http) throws Exception{
        // http.authorizeRequests().antMatchers("/").permitAll();
        
        http.authorizeRequests().antMatchers("/")
            .authenticated()
            .anyRequest()
            .permitAll()
        .and() 
        .antMatcher("/loginadmin")
            .authorizeHttpRequests().anyRequest().hasAnyAuthority("ADMIN")
            .and()
            .formLogin()
                .loginPage("/loginadmin")
                // .loginProcessingUrl("/loginadmin")
                // .defaultSuccessUrl("/")
                // .usernameParameter("username")
                .permitAll()
            .and()
            .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logoutadmin"))
                .logoutSuccessUrl("/loginadmin?logout")
                .permitAll();
    }
}