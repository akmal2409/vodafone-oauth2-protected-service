package com.akmal.vodafoneoauth2protectedservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
               .authorizeHttpRequests()
               .requestMatchers("/api/v1/echo/**")
               .hasAuthority("SCOPE_vz_test")
               .anyRequest()
               .authenticated()
               .and()
               .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
               .build();
  }
}
