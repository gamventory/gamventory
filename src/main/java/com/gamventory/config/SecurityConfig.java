package com.gamventory.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.gamventory.repository.MemberService;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    MemberService memberService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin(formLoginConfigurer -> {
            formLoginConfigurer
                    .loginPage("/members/login")
                    .defaultSuccessUrl("/")
                    .usernameParameter("email")
                    .failureUrl("/members/login/error");
        })
        .logout(logoutConfigurer -> {
            logoutConfigurer
                    .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                    .logoutSuccessUrl("/");
        })
        .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
                .requestMatchers("/", "/members/**", "/item/**", "/images/**").permitAll()
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
        )
        .exceptionHandling(exceptionHandlingConfigurer -> {
            exceptionHandlingConfigurer
                    .authenticationEntryPoint(new CustomAuthenticationEntryPoint());
        });
        

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

     @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}