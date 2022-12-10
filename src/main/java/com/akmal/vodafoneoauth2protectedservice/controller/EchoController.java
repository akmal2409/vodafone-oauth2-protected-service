package com.akmal.vodafoneoauth2protectedservice.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/echo")
public class EchoController {

  @RequestMapping(value = "/**")
  public void echo(HttpServletRequest httpServletRequest) {

  }
}
