package com.gustavo.produtos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/cadastro", "/salvarUsuario", "/h2-console/**").permitAll()
                .requestMatchers("/usuarios/**").hasRole("ADMIN")
                .requestMatchers("/deletar/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )

            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )

            .logout(logout -> logout
                .logoutSuccessUrl("/login")
            )

            // 🔐 Página personalizada de acesso negado
            .exceptionHandling(ex -> ex
                .accessDeniedPage("/403")
            );

        // H2 Console funcionar mesmo com login
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));
        http.csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
