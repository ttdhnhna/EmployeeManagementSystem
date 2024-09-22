package com.practiceproject.EmployeeManagementSystem.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
    DataSource dataSource;
    @Autowired
    private CustomLogoutSuccessHandler logout;

    @Bean
    public UserDetailsService userDetailsService(){
        return new CustomUserDetailsService();
    }
    // Purpose: The UserDetailsService interface is a core interface in Spring Security used to retrieve user-specific data.
    // What it does: It returns a custom implementation (CustomUserDetailsService) that loads the user details (such as username, password, roles, etc.) from your data source, like a database.
    // Why it's needed: When a user tries to log in, Spring Security will call this service to load the user’s data, which will then be used for authentication and authorization.

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    // Purpose: Password encoding is critical to storing passwords securely.
    // What it does: This creates a BCryptPasswordEncoder, a widely-used password hashing algorithm that securely hashes passwords before storing them in the database.
    // Why it's needed: When a user tries to log in, the password they enter will be hashed using the same algorithm and compared to the hashed password in the database. BCrypt is preferred because it's computationally expensive, making it more resistant to brute-force attacks.

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
    // Purpose: This method configures Spring Security’s authentication mechanism.
    // What it does: It tells Spring Security to use the custom DaoAuthenticationProvider (defined above) for authentication. When a user tries to log in, Spring Security will use this provider to:
    // Load the user details via CustomUserDetailsService.
    // Verify the user’s password using BCryptPasswordEncoder.
    // Why it's needed: By specifying the custom authentication provider, this ensures that Spring Security uses the configured components for authentication, allowing custom user detail loading and password encoding logic.


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").hasRole("MANAGER")//Hóa ra đây là chỗ yêu cầu cần đăng nhập mới có quyền truy cập. Câu lệnh này sẽ chỉnh đường dẫn đc thêm sẽ làm những gì. 
                .antMatchers("/employeepage").hasRole("EMPLOYEE")
                .antMatchers("/employee/login").permitAll()
                .antMatchers("/manager/login").permitAll()
                .anyRequest().authenticated()//Xác định danh tính người định truy cập đường link đc chỉ định hay là đường link ở trên.
            .and()
            .formLogin()
                .loginPage("/manager/login")
                .loginProcessingUrl("/manager/login")
                .defaultSuccessUrl("/")
                .permitAll()
            .and()
            .formLogin()
                .loginPage("/employee/login")
                .defaultSuccessUrl("/employeepage")
                .permitAll()
            .and()
            .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessHandler(logout)
                .permitAll()
            .and()
            .rememberMe()
                .key("AbcdefghijKlmno1234567890")
                .tokenValiditySeconds(24 * 60 * 60);
    } 
}
