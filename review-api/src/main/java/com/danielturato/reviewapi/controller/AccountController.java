package com.danielturato.reviewapi.controller;

import com.danielturato.reviewapi.dto.AccountDto;
import com.danielturato.reviewapi.dto.AuthRequestDto;
import com.danielturato.reviewapi.dto.AuthResponseDto;
import com.danielturato.reviewapi.dto.NewAccountDto;
import org.springframework.web.bind.annotation.*;

public interface AccountController {

    @GetMapping("/{id}")
    AccountDto getAccount(@PathVariable String id);

    @PostMapping("/")
    AccountDto createAccount(NewAccountDto newAccountDto);

    @PutMapping("/{id}")
    AccountDto updateAccount(AccountDto accountDto);

    @PostMapping("/auth")
    AuthResponseDto authenticate(@RequestBody AuthRequestDto authRequestDto);
}
