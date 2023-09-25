package com.kh.Freepets.security;

import com.kh.Freepets.domain.member.Member;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@EnableWebSecurity
public class WebSecurityConfig
{
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception
    {
        httpSecurity.authorizeHttpRequests();
        httpSecurity.cors().and()
                .csrf().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeHttpRequests()
                .requestMatchers(new AntPathRequestMatcher("/api/auth/signup")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/api/auth/signin")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/api/auth/member/{id}")).hasAnyRole("ROLE_USER")
                .anyRequest().authenticated();

        httpSecurity.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);
        return httpSecurity.build();
    }
}
