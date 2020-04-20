package com.mmabas77.config;

import com.mmabas77.backend.service.UserSecurityService;
import com.mmabas77.web.controllers.ForgotMyPasswordController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserSecurityService userSecurityService;

    @Autowired
    private Environment env;

    //Don't Touch Again->For BCrypt//
    private static final String SALT = "enocr[,uvhtecr[kh6y58mo8n7ij6ub5vycy4vu5b6";

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(
                12,//The Longer The Better But Slower//
                new SecureRandom(SALT.getBytes())
        );
    }

    public static final String[] PUBLIC_URLS = {

            /*----  Resources  ----*/
            "/webjars/**",
            "/css/**",
            "/js/**",
            "/img/**",
            /*----  Home  ----*/
            "/",
            /*----  About  ----*/
            "/about/**",
            /*----  Contact  ----*/
            "/contact/**",
            /*----  Forgot Password  ----*/
            ForgotMyPasswordController.FORGOT_PASSWORD_URL_MAPPING,
            /*----  Error  ----*/
            "/error/**/*",
            /*----  Console-(For H2 DB)  ----*/
            "/console/**",
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        List<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains("dev")) {
            http.csrf().disable();
            http.headers().frameOptions().disable();
        }
        http
                .authorizeRequests()
                .antMatchers(PUBLIC_URLS).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").defaultSuccessUrl("/payload")
                .failureUrl("/login?error")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
    }
}

