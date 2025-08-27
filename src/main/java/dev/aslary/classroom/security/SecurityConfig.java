package dev.aslary.classroom.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
      .authorizeHttpRequests(auth ->
        auth
          .requestMatchers(
            "/",
            "index.html",
            "favicon.ico",
            "main.css",
            "/assets/**",
            "/api/auth/login",
            "{path:^(?!api|assets)[^\\.]*}/**"
          )
          .permitAll()
          .requestMatchers("/h2-console/**")
          .hasRole(Role.Fields.ADMIN)
          .anyRequest()
          .authenticated()
      )
      .csrf(csrf ->
        csrf
          .ignoringRequestMatchers("/h2-console/**")
          .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
          .csrfTokenRequestHandler(new SpaCsrfTokenRequestHandler())
      )
      .cors(Customizer.withDefaults())
      .formLogin(login -> login.disable())
      .logout(logout -> logout.disable())
      .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
      .build();
  }

  @Bean
  UserDetailsManager userDetailsManager() {
    var user = User.withUsername("user").password("{noop}user").roles(Role.Fields.USER).build();
    var admin = User.withUsername("admin").password("{noop}admin").roles(Role.Fields.ADMIN, Role.Fields.USER).build();

    return new InMemoryUserDetailsManager(user, admin);
  }

  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
    throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
}
