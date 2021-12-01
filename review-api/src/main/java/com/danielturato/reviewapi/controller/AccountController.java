package com.danielturato.reviewapi.controller;

import com.danielturato.reviewapi.dto.AccountDto;
import com.danielturato.reviewapi.dto.NewAccountDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public interface AccountController {

    @GetMapping("/{id}")
    AccountDto getAccount(@PathVariable String id);

    @PostMapping("/")
    AccountDto createAccount(NewAccountDto newAccountDto);
}
