package com.security.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceController {

  @GetMapping("/myBalance")
  public String getBalanceDetails() {
    return "balance DB";
  }
}
