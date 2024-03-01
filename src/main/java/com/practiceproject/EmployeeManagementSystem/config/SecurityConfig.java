package com.practiceproject.EmployeeManagementSystem.config;

// import javax.sql.DataSource;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
// import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.practiceproject.EmployeeManagementSystem.service.CustomUserDetailsService;

// import com.practiceproject.EmployeeManagementSystem.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // @Autowired
    // DataSource dataSource;

    // @Bean
    // public UserDetailsService userDetailsService(){
    //      return new CustomUserDetailsService();
    //  }

    // @Bean
    // public BCryptPasswordEncoder passwordEncoder() {
    //     return new BCryptPasswordEncoder();
    // }

    // @Override
    // protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //     auth.authenticationProvider(authenticationProvider());
    // }

    // @Bean
    // public DaoAuthenticationProvider authenticationProvider() {
    //     DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    //     authProvider.setUserDetailsService(userDetailsService());
    //     authProvider.setPasswordEncoder(passwordEncoder());
    //     return authProvider;
    // }

    // @Override
    // protected void configure(HttpSecurity http) throws Exception {
    //     http.authorizeRequests()
    //             .antMatchers("/login").hasAuthority("USER")
    //             .antMatchers("/userpage").authenticated()//Nếu sai thi xoa cai nay di
    //             .antMatchers("/loginadmin").hasAuthority("ADMIN")
    //             .antMatchers("/").authenticated()
    //             .anyRequest().permitAll()
    //             .and()
    //         .formLogin()
    //             .loginPage("/login")
    //             .loginProcessingUrl("/login")
    //             .defaultSuccessUrl("/userpage")
    //             // .usernameParameter("username")
    //             .permitAll()
    //             .and()
    //         .formLogin()
    //             .loginPage("/loginadmin")
    //             .loginProcessingUrl("/loginadmin")
    //             .defaultSuccessUrl("/")
    //             // .usernameParameter("username")
    //             .permitAll()
    //             .and()
    //         .logout()
    //             .invalidateHttpSession(true)
    //             .clearAuthentication(true)
    //             .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
    //             .logoutSuccessUrl("/login?logout")
    //             .permitAll()
    //             //Có thêm đoạn dưới này
    //             .logoutRequestMatcher(new AntPathRequestMatcher("/logoutadmin"))
    //             .logoutSuccessUrl("/loginadmin?logout")
    //             .permitAll()
    //         ;
    // }
    
    @Bean
    public UserDetailsService userDetailsService(){
        return new CustomUserDetailsService();
    }

    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {//Dung de xac thuc admin vs user
        auth.authenticationProvider(adminAuthenticationProvider());
        auth.authenticationProvider(userAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/loginadmin").hasAuthority("ADMIN")
                .antMatchers("/").hasAuthority("ADMIN")
                .antMatchers("/login").hasAuthority("USER")
                .antMatchers("/userpage").hasAuthority("USER")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logoutadmin"))
                .logoutSuccessUrl("/loginadmin?logout")
                .and()
            .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll();
    }

    @Bean
    public DaoAuthenticationProvider adminAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        // authProvider.setAuthoritiesMapper(adminAuthoritiesMapper());
        return authProvider;
    }

    @Bean
    public DaoAuthenticationProvider userAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // @Bean
    // public GrantedAuthoritiesMapper adminAuthoritiesMapper() {
    //     SimpleAuthorityMapper authorityMapper = new SimpleAuthorityMapper();
    //     authorityMapper.setPrefix("ROLE_");
    //     authorityMapper.setConvertToUpperCase(true);
    //     return authorityMapper;
    // }
}

