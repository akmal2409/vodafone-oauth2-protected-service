package com.akmal.vodafoneoauth2protectedservice.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.HttpWaitStrategy;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.junit.jupiter.TestcontainersExtension;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public class EchoControllerIT {

  @Autowired
  private MockMvc mockMvc;

  @Container
  static GenericContainer<?> echoContainer =
      new GenericContainer<>(DockerImageName.parse("ealen/echo-server:0.7.0"))
          .withExposedPorts(80)
          .waitingFor(Wait.forHttp("/test?echo_code=200").forStatusCode(200));

  static {
    echoContainer.start();
  }


  @DynamicPropertySource
  static void initProperties(DynamicPropertyRegistry registry) {
    registry.add("proxy.echo-host", () -> String.format("http://localhost:%d", echoContainer.getFirstMappedPort()));
  }

  @Test
  @DisplayName("Should proxy request when scope vz_test is present and subject claim")
  void shouldProxyRequestWhenScopeIsPresent() throws Exception {
    mockMvc.perform(get(EchoController.BASE_API)
                        .with(oidcLogin()
                                  .authorities(new SimpleGrantedAuthority("SCOPE_vz_test"))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("http.method").value("GET"))
        .andExpect(jsonPath("http.baseUrl").value(""))
        .andExpect(jsonPath("http.originalUrl").value(EchoController.BASE_API));

  }

  @Test
  @DisplayName("Should return 403 when scope is missing but request is authenticated")
  void shouldReturn403WhenScopeIsMissing() throws Exception {
    mockMvc.perform(get(EchoController.BASE_API)
                        .with(oidcLogin()
                                  .authorities()))
        .andExpect(status().is(403));

  }

  @Test
  @DisplayName("When request is not authenticated")
  void shouldReturn401WhenScopeIsMissing() throws Exception {
    mockMvc.perform(get(EchoController.BASE_API))
        .andExpect(status().is(401));

  }
}
