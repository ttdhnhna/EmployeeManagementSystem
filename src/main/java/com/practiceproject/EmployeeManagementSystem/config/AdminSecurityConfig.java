package com.practiceproject.EmployeeManagementSystem.config;

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
// import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.practiceproject.EmployeeManagementSystem.service.CustomUserDetailsService;

import javax.sql.DataSource;

@Configuration
@Order(1)
@EnableWebSecurity
public class AdminSecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
    DataSource dataSource;

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
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
    
    @Override 
    protected void configure(HttpSecurity http) throws Exception{
        // http.authorizeRequests().antMatchers("/").permitAll();
        
         http.antMatcher("/admin/**")
             .authorizeHttpRequests().anyRequest().hasAuthority("ADMIN")
             .and()
             .formLogin()
                 .loginPage("/admin/login")
                 .usernameParameter("username")
                 .loginProcessingUrl("/admin/login")
                 .defaultSuccessUrl("/admin/login")
//                 .permitAll()
             .and()
             .logout()
                //  .invalidateHttpSession(true)
                //  .clearAuthentication(true)
                //  .logoutRequestMatcher(new AntPathRequestMatcher("/logoutadmin"))
                 .logoutUrl("/admin/logout")
                 .logoutSuccessUrl("/admin/logout?logout");
                //  .permitAll();

//        http.authorizeRequests()
//                .antMatchers("/").hasAuthority("ADMIN") // Access to homepage restricted to ADMIN
//                .antMatchers("/loginadmin").hasAnyAuthority("ADMIN") // Allow access to loginadmin page
//                .anyRequest()
//                .authenticated()
//                .and()
//            .formLogin()
//                .loginPage("/loginadmin")
//                .loginProcessingUrl("/loginadmin")
//                .permitAll()
//                .and()
//            .logout()
//                .invalidateHttpSession(true)
//                .clearAuthentication(true)
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logoutadmin"))
//                .logoutSuccessUrl("/loginadmin?logout")
//                .permitAll();
    }
}
