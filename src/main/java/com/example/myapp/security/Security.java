package com.example.myapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class Security {
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return (
            httpSecurity
            .authorizeHttpRequests((req) -> {
                req.requestMatchers("/users/admin");
                req.requestMatchers("/users/user");
                req.requestMatchers("/users/save").permitAll();
                req.requestMatchers("/users/all").permitAll();
                req.requestMatchers("/users/delete/*").permitAll();
                req.anyRequest().authenticated();
            })
            .csrf((csrf) -> {
                csrf.disable();
            })
            .formLogin((form) -> {
                form.permitAll();
                form.failureHandler(authenticationFailureHandler());
            })
            .sessionManagement((session) -> {
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            })
            .httpBasic(Customizer.withDefaults())
            .build()
        );
    }

    @Bean
    WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*");
            }
        };
    }

    @Bean
    AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }
}
