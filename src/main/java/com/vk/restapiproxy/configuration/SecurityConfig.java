package com.vk.restapiproxy.configuration;

import com.vk.restapiproxy.database.entity.User;
import com.vk.restapiproxy.database.repository.UserRepository;
import com.vk.restapiproxy.handler.AuditAccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuditAccessDeniedHandler accessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            User user = userRepository.findByUsername(username);
            if (user != null) return user;
            throw new UsernameNotFoundException("User ‘" + username + "’ not found");
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register").anonymous()
                        .requestMatchers("/home").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/posts", "/api/posts/{id}", "/api/posts?id={id}").hasAnyRole("POSTS_VIEWER", "POSTS_EDITOR", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/posts", "/api/posts/{id}", "/api/posts?id={id}").hasAnyRole("POSTS_EDITOR", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/posts", "/api/posts/{id}", "/api/posts?id={id}").hasAnyRole("POSTS_EDITOR", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/posts", "/api/posts/{id}", "/api/posts?id={id}").hasAnyRole("POSTS_EDITOR", "ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/users", "/api/users/{id}", "/api/users?id={id}").hasAnyRole("USERS_VIEWER", "USERS_EDITOR", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/users", "/api/users/{id}", "/api/users?id={id}").hasAnyRole("USERS_EDITOR", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/users", "/api/users/{id}", "/api/users?id={id}").hasAnyRole("USERS_EDITOR", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/users", "/api/users/{id}", "/api/users?id={id}").hasAnyRole("USERS_EDITOR", "ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/albums", "/api/albums/{id}", "/api/albums?id={id}").hasAnyRole("ALBUMS_VIEWER", "ALBUMS_EDITOR", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/albums", "/api/albums/{id}", "/api/albums?id={id}").hasAnyRole("ALBUMS_EDITOR", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/albums", "/api/albums/{id}", "/api/albums?id={id}").hasAnyRole("ALBUMS_EDITOR", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/albums", "/api/albums/{id}", "/api/albums?id={id}").hasAnyRole("ALBUMS_EDITOR", "ADMIN")
                        .requestMatchers("/**").permitAll()
                )
                .formLogin((formLogin) ->
                        formLogin
                                .usernameParameter("username")
                                .passwordParameter("password")
                                .successForwardUrl("/home").failureForwardUrl("/home"))
                .logout((logout) ->
                        logout.deleteCookies("remove")
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/home")
                )
                .exceptionHandling((eh) -> eh.accessDeniedHandler(accessDeniedHandler))
                .build();
    }
}
