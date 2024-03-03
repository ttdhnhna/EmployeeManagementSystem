package com.practiceproject.EmployeeManagementSystem.config;

 import org.springframework.context.annotation.Configuration;
 import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@Order(2)
@EnableWebSecurity
public class UserSecurityConfig extends WebSecurityConfigurerAdapter{

    @Override
    protected void configure(HttpSecurity http) throws Exception {
         http.authorizeRequests().antMatchers("/userpage")
             .authenticated()
             // .anyRequest()
             // .permitAll()
         .and()
         .antMatcher("/login")
             .authorizeRequests()
             .anyRequest().hasAuthority("USER")
         .and()
         .formLogin()
            .loginPage("/login")
            .usernameParameter("username")
            .loginProcessingUrl("/login")
            .defaultSuccessUrl("/login")
            .permitAll()
         .and()
         .logout()
            //  .invalidateHttpSession(true)
            //  .clearAuthentication(true)
            //  .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
             .logoutUrl("/logout")
             .logoutSuccessUrl("/login?logout")
             .permitAll();
//        http.authorizeRequests()
//                .antMatchers("/userpage").hasAuthority("USER") // Access to homepage restricted to ADMIN
//                .antMatchers("/login").hasAuthority("USER") // Allow access to loginadmin page
//                .anyRequest().authenticated()
//                .and()
//            .formLogin()
//                .loginPage("/login")
//                .defaultSuccessUrl("/userpage")
//                .permitAll()
//                .and()
//            .logout()
//                .invalidateHttpSession(true)
//                .clearAuthentication(true)
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("/login?logout")
//                .permitAll();
    }
    
}
