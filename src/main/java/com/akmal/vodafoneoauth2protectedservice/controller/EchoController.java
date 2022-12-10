package com.akmal.vodafoneoauth2protectedservice.controller;



import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1/echo")
public class EchoController {

  private final RestTemplate restTemplate;

  public EchoController(@Qualifier("echoRestTemplate") RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @RequestMapping(value = "/**")
  public ResponseEntity<?> echo(RequestEntity<?> entity, HttpServletRequest request) {
    RequestEntity<?> proxyEntity = stripCredentials(entity);

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

  private RequestEntity<?> stripCredentials(RequestEntity<?> requestEntity) {
    final var headers = HttpHeaders.writableHttpHeaders(requestEntity.getHeaders());
    headers.remove("Authorization");
    return new RequestEntity<>(
        requestEntity.getBody(),
        headers,
        requestEntity.getMethod(),
        URI.create(requestEntity.getUrl().getPath()),
        requestEntity.getType());
  }
}
