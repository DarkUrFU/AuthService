package com.darkurfu.authservice.config;

import com.darkurfu.authservice.datamodels.enums.Permissions;
import com.darkurfu.authservice.datamodels.enums.Services;
import com.darkurfu.authservice.service.authutils.GrantedAuthUtil;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;



@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    public SpringSecurityConfig(){    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests(
                        (auth) -> auth
                                .requestMatchers(HttpMethod.GET, "api/web/v1/auth/ban/**").hasAnyAuthority(
                                        GrantedAuthUtil.getAuthPermissionStr(Services.AUTH_SERVICE, Permissions.READ),
                                        GrantedAuthUtil.getAuthPermissionStr(Services.AUTH_SERVICE, Permissions.COMMIT),
                                        GrantedAuthUtil.getAuthPermissionStr(Services.AUTH_SERVICE, Permissions.ALL)
                                )
                                .requestMatchers(HttpMethod.POST, "api/web/v1/auth/ban/**").hasAnyAuthority(
                                        GrantedAuthUtil.getAuthPermissionStr(Services.AUTH_SERVICE, Permissions.COMMIT),
                                        GrantedAuthUtil.getAuthPermissionStr(Services.AUTH_SERVICE, Permissions.ALL)
                                )
                                .requestMatchers(HttpMethod.DELETE, "api/web/v1/auth/ban/**").hasAnyAuthority(
                                        GrantedAuthUtil.getAuthPermissionStr(Services.AUTH_SERVICE, Permissions.COMMIT),
                                        GrantedAuthUtil.getAuthPermissionStr(Services.AUTH_SERVICE, Permissions.ALL)
                                )
                                .requestMatchers("/api/web/v1/auth/edit/**").authenticated()
                                .requestMatchers("/api/web/v1/auth/session/**").authenticated()
                                .requestMatchers("/api/web/v1/auth/authentication/logout").authenticated()


                                .anyRequest().permitAll()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);


        return http.getOrBuild();
    }



    @Bean
    public  JwtAuthFilter jwtAuthenticationFilter() {
        return new JwtAuthFilter();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(10))
                .build();
    }

}
