package com.practiceproject.EmployeeManagementSystem.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.core.annotation.Order;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// import com.practiceproject.EmployeeManagementSystem.service.CustomUserDetailsService;

// @Configuration
// @Order(2)
public class AdminSecurityConfig {
    // @Bean
    // public UserDetailsService userDetailsService(){
    //     return new CustomUserDetailsService();
    // }

    // @Bean
    // public BCryptPasswordEncoder passwordEncoder(){
    //     return new BCryptPasswordEncoder();
    // }

    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    //     http.authorizeRequests().antMatchers("/")
    //         .permitAll();
    //     http.antMatcher("/loginadmin")
    //         .authorizeRequests().anyRequest()
    //         .hasAuthority("ADMIN")
    //         .and()
    //         .formLogin()
    //             .loginPage("loginadmin")
    //             .usernameParameter("email")
    //             .permitAll()
    //         .and()
    //         .logout()
    //             .invalidateHttpSession(true)
    //             .clearAuthentication(true)
    //             .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
    //             .logoutSuccessUrl("/login?logout")
    //             .permitAll();
    //     return http.build();
    // }
}
