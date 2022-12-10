package com.akmal.vodafoneoauth2protectedservice.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("proxy")
@Getter
@Setter
public class ProxyConfigurationProperties {

  /**
   * Hostname of the echo server.
   */
  private String echoHost;
}
