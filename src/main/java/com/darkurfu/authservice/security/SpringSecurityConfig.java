package com.darkurfu.authservice.security;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    public SpringSecurityConfig(){    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests((auth) -> auth
                .requestMatchers("/edit/**").authenticated()
                .anyRequest().permitAll()
        );




        //http.addFilter(jwtAuthenticationFilter());

        //http.addFilterBefore(jwtAuthenticationFilter(), AbstractAuthenticationProcessingFilter.class);
        return http.build();
    }

    @Bean
    public  JwtAuthFilter jwtAuthenticationFilter() {
        return new JwtAuthFilter();
    }
}
