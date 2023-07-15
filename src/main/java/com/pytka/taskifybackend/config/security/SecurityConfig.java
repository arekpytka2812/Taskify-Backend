package com.pytka.taskifybackend.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.RequestMapping;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(
        jsr250Enabled = true,
        securedEnabled = true
)
public class SecurityConfig {

    private final AuthenticationProvider authProvider;

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    @Profile("dev")
    protected SecurityFilterChain devFilter(HttpSecurity http) throws Exception {

        enableH2Access(http)
                .authorizeHttpRequests()
                .anyRequest().permitAll().and()
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .logout().disable()
                .headers().frameOptions().disable();


        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

    @Profile({"security", "prod"})
    @Bean
    public SecurityFilterChain securityTestSecurityFilterChain(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/auth/register")
                .permitAll()
                .requestMatchers("/auth/login")
                .permitAll()
                .requestMatchers("/swagger-ui/**")
                .permitAll()
                .requestMatchers("/v3/api-docs/**")
                .permitAll();

        enableH2Access(http).authorizeHttpRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private HttpSecurity enableH2Access(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests()
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**"))
                .permitAll();
        http.headers().frameOptions().disable();
        return http;
    }
}
