package com.example.parking_system_orange_innovation.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private final JwtAuthEntryPoint jwtAuthEntryPoint;

    public SecurityConfig(JwtAuthEntryPoint jwtAuthEntryPoint) {
        this.jwtAuthEntryPoint = jwtAuthEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .cors()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
//                .antMatchers("/cars/**").hasAuthority("ADMIN")
//                .antMatchers("/clients/**").hasAuthority("ADMIN")
//                .antMatchers("/slots/getSlots").hasAuthority("ADMIN")
//                .antMatchers("/slots/addSlot").hasAuthority("ADMIN")
//                .antMatchers("/slots/freeSlots").hasAuthority("ADMIN")
//                .antMatchers("/slots/updateSlot").hasAuthority("ADMIN")
//                .antMatchers("/slots/assignSlot/**").hasAuthority("ADMIN")
//                .antMatchers("/slots/deleteSlot").hasAuthority("ADMIN")
//                .antMatchers("/slots/getAvailableSlots").hasAuthority("ADMIN")
                .anyRequest().authenticated()

                .and()
                .httpBasic();

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {

        return authenticationConfiguration.getAuthenticationManager();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();

    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

}
