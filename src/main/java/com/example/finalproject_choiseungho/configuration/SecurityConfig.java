package com.example.finalproject_choiseungho.configuration;

import com.example.finalproject_choiseungho.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;

    @Value("${jwt.secret}")
    private String secretKey;

    private final String[] SWAGGER = {
            "/v3/api-docs",
            "/swagger-resources/**", "/configuration/security", "/webjars/**",
            "/swagger-ui.html", "/swagger/**", "/swagger-ui/**"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .cors().and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt사용하는 경우 씀
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/hello/**").permitAll()
                .antMatchers(SWAGGER).permitAll()
                .antMatchers("/api/v1/users/join", "/api/v1/users/login").permitAll() // join, login은 언제나 가능
                .antMatchers("/api/v1/posts/my").authenticated()
                .antMatchers(HttpMethod.GET, "/api/v1/posts/**").permitAll()
                .antMatchers("/api/**").authenticated()
                .and()
                .addFilterBefore(new JwtFilter(userService, secretKey), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}