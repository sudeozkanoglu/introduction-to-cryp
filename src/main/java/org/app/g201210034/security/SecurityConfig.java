package org.app.g201210034.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/login/v1/auth/**").permitAll()
                                .requestMatchers("/car/v1/saveCarWithPhoto/**").permitAll()
                                .requestMatchers("/login/v1/save/**").permitAll()
                                .requestMatchers("/user/v1/save/**").permitAll()
                                .requestMatchers("/car/v1/getAllCars/**").hasRole("ADMIN")
                                .requestMatchers("/car/v1/getCarById/**").permitAll()
                                .requestMatchers("/user/v1/getUserById/**").hasAnyRole("ADMIN", "USER")
                                .anyRequest().hasRole("ADMIN")
                );//.exceptionHandling((exception)-> exception.authenticationEntryPoint(new CustomAuthenticationEntryPoint()));

        //more will come
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
