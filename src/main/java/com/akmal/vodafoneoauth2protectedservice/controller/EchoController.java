package com.akmal.vodafoneoauth2protectedservice.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(EchoController.BASE_API)
public class EchoController {

  protected static final String BASE_API = "/api/v1/echo";
  private final RestTemplate restTemplate;

  public EchoController(@Qualifier("echoRestTemplate") RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @RequestMapping(value = "/**")
  public ResponseEntity<?> echo(RequestEntity<?> entity, HttpServletRequest request) {
    try {
      return restTemplate.exchange(entity.getUrl().getPath(),
          entity.getMethod(),
          new HttpEntity<>(entity.getBody()),
          Object.class);
    } catch (HttpClientErrorException e) {
      return ResponseEntity
                 .status(e.getStatusCode())
                 .headers(e.getResponseHeaders())
                 .body(e.getResponseBodyAsString());
    }
  }
}
