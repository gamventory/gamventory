package com.gamventory.config;

import com.gamventory.service.OAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2UserService oAuth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
//                .csrf(AbstractHttpConfigurer::disable)
                .csrf((csrf) -> csrf
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/mail/**")))
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/members/login")
                                .defaultSuccessUrl("/")
                                .usernameParameter("email")
                                .failureUrl("/members/login/error")
                )
                .logout(logout ->
                        logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                                .logoutSuccessUrl("/")
                )
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(new AntPathRequestMatcher("/css/**"),
                                        new AntPathRequestMatcher("/js/**"),
                                        new AntPathRequestMatcher("/favicon.ico"),
                                        new AntPathRequestMatcher("/img/**"),
                                        new AntPathRequestMatcher("/"),
                                        new AntPathRequestMatcher("/members/login"),
                                        new AntPathRequestMatcher("/members/login/oauth2/**"),
                                        new AntPathRequestMatcher("/members/new"),
                                        new AntPathRequestMatcher("/members/find/**"),
                                        new AntPathRequestMatcher("/item/**"),
                                        new AntPathRequestMatcher("/serials/**"),
                                        new AntPathRequestMatcher("/api/**"),
                                        new AntPathRequestMatcher("/images/**"),
                                        new AntPathRequestMatcher("/error/**"),
                                        new AntPathRequestMatcher("/mail/**"),
                                        new AntPathRequestMatcher("/order/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/members/**")).authenticated()
                                .requestMatchers(new AntPathRequestMatcher("/admin/**")).hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                )
                // 언제 세션을 생성 할 것인지 알려 주는 설정
                .sessionManagement(session -> session
                        // ALWAYS, IF_REQUIRED, NEVER, STATLESS
                        // ALWAYS 항상 새로운 세션을 사용하도록
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .oauth2Login(oAuth2LoginConfigurer -> oAuth2LoginConfigurer
                        .loginPage("/members/login")
                        .defaultSuccessUrl("/")
                        .failureUrl("/members/login/error")
                        .userInfoEndpoint(endpoint -> endpoint
                                .userService(oAuth2UserService)));

                /*
                현재 세션 상황을 ALWAYS로 설정 했음
                어디 까지 영향이 갈지 모르겠음
                 */


        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }

}


