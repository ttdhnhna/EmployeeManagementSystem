// package com.practiceproject.EmployeeManagementSystem.config;

// // import javax.sql.DataSource;

// // import com.practiceproject.EmployeeManagementSystem.service.CustomUserDetailsService;
// // import org.springframework.beans.factory.annotation.Autowired;
// // import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.core.annotation.Order;
// // import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// // import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// // import org.springframework.security.core.userdetails.UserDetailsService;
// // import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// @Configuration
// @Order(1)
// @EnableWebSecurity
// public class EmployeeSecurityConfig extends WebSecurityConfigurerAdapter{
//     @Override
//     protected void configure(HttpSecurity http) throws Exception {
//         http.authorizeRequests()
//                 .antMatchers("/employee/**").hasRole("EMPLOYEE")
//                 .antMatchers("/styles/**", "/image/**"
//                         , "/upforgotpassword", "/resetpassword"
//                         , "/upresetpassword", "/manager/login"
//                         , "/forgotpassword"
//                 ).permitAll()
//                 .anyRequest().authenticated()
//                 .and()
//             .formLogin()
//                 .loginPage("/employee/login")
//                 .usernameParameter("username")
//                 .loginProcessingUrl("/employee/login")
//                 .defaultSuccessUrl("/employee/home")
//                 .permitAll()
//             .and()
//             .logout()
//                 .invalidateHttpSession(true)
//                 .clearAuthentication(true)
//                 .logoutRequestMatcher(new AntPathRequestMatcher("/employee/logout"))
//                 .permitAll()
//             .and()
//             .rememberMe()
//                 .key("AbcdefghijKlmno1234567890")
//                 .tokenValiditySeconds(24 * 60 * 60);
//     }
// }
