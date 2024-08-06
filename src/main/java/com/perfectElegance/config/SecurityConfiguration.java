package com.perfectElegance.config;

import com.perfectElegance.filter.JwtAuthenticationFilter;
import com.perfectElegance.service.UserDetailsServiceIMPL;
import org.modelmapper.ModelMapper;
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
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {


    private final UserDetailsServiceIMPL userDetailsServiceIMPL;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfiguration(UserDetailsServiceIMPL userDetailsServiceIMPL, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsServiceIMPL = userDetailsServiceIMPL;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        req -> req.requestMatchers("/auth/**","/otp/**","/user/ws/**")
                                .permitAll()
                                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                                .requestMatchers("/user/**").hasAuthority("USER")
                                .anyRequest()
                                .authenticated()
                )
                .userDetailsService(userDetailsServiceIMPL)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();

    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
