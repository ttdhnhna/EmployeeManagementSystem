package com.practiceproject.EmployeeManagementSystem.config;

// import javax.sql.DataSource;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.core.annotation.Order;
// import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// // import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// import com.practiceproject.EmployeeManagementSystem.service.CustomUserDetailsService;

// @Configuration
// @EnableWebSecurity
// @Order(1)

public class UserSecurityConfig {
    // @Autowired
    // DataSource dataSource;

    // @Bean
    // public UserDetailsService userDetailsService(){
    //     return new CustomUserDetailsService();
    // }

    // @Bean
    // public BCryptPasswordEncoder passwordEncoder(){
    //     return new BCryptPasswordEncoder();
    // }

    // @Bean
    // public DaoAuthenticationProvider authenticationProvider(){
    //     DaoAuthenticationProvider authProvider=new DaoAuthenticationProvider();
    //     authProvider.setUserDetailsService(userDetailsService());
    //     authProvider.setPasswordEncoder(passwordEncoder());
    //     return authProvider;    
    // }

    // @Override
    // protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //     auth.authenticationProvider(authenticationProvider());
    // }

    // @Override
    // protected void configure(HttpSecurity http) throws Exception {
    //     http.authorizeRequests()
    //         .antMatchers("/")//Hóa ra đây là chỗ yêu cầu cần đăng nhập mới có quyền truy cập. Câu lệnh này sẽ chỉnh đường dẫn đc thêm sẽ làm những gì. 
    //         .authenticated()//Xác định danh tính người định truy cập đường link đc chỉ định hay là đường link ở trên.
    //         .anyRequest().permitAll()
    //         .and()
    //         .formLogin()
    //             .loginPage("/login")
    //             .permitAll()
    //         .and()
    //         .logout()
    //             .invalidateHttpSession(true)
    //             .clearAuthentication(true)
    //             .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
    //             .logoutSuccessUrl("/login?logout")
    //             .permitAll();
    // }

    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    //     http.authorizeRequests().antMatchers("/")
    //         .authenticated()
    //         .anyRequest()
    //         .permitAll();
    //     http.antMatcher("/login")
    //         .authorizeRequests().anyRequest()
    //         .hasAuthority("USER")
    //         .and()
    //         .formLogin()
    //             .loginPage("/login")
    //             .usernameParameter("username")
    //             .loginProcessingUrl("/login")
    //             .defaultSuccessUrl("/userpage")
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
