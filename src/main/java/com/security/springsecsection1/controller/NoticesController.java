package com.security.springsecsection1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoticesController {

  @GetMapping("/notices")
  public String myNotice() {
    return "Notices";
  }
}
