package com.cavdarfurkan.librarymanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(request -> request
                .requestMatchers("/", "/books/**").permitAll()
                .requestMatchers("/register", "/register-reader", "/register-publisher").permitAll()
                .requestMatchers("/admin/**").hasAnyRole("ADMINISTRATOR")
                .requestMatchers("/librarian/**").hasAnyRole("LIBRARIAN")
                .requestMatchers("/publisher/**").hasAnyRole("PUBLISHER")
                .requestMatchers("/reader/**").hasAnyRole("READER")
                .anyRequest().authenticated()
        );

        http.anonymous(user -> user.authorities("UNREGISTERED_USER"));

        http.formLogin(login -> login
                .loginPage("/login")
                .successHandler(customSuccessHandler())
                .permitAll());

        http.logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout/**", HttpMethod.GET.name()))
                .logoutSuccessUrl("/")
        );

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler customSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_ADMINISTRATOR > ROLE_LIBRARIAN > ROLE_PUBLISHER > ROLE_READER > ROLE_UNREGISTERED_USER";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new CustomPasswordEncoder();
    }
}
