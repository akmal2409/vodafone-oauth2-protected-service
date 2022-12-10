package com.akmal.vodafoneoauth2protectedservice.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class ProxyConfiguration {
  private final ProxyConfigurationProperties proxyConfigurationProperties;


  @Bean("echoRestTemplate")
  public RestTemplate restTemplate() {
    return new RestTemplateBuilder()
               .rootUri(proxyConfigurationProperties.getEchoHost())
               .build();
  }
}
