package com.security.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

  @GetMapping("/myAccount")
  public String getAccountDetails() {
    return "account details from the DB";
  }
}
