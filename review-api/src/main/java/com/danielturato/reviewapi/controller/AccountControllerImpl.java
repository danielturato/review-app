package com.danielturato.reviewapi.controller;

import com.danielturato.reviewapi.dto.AccountDto;
import com.danielturato.reviewapi.dto.AuthRequestDto;
import com.danielturato.reviewapi.dto.AuthResponseDto;
import com.danielturato.reviewapi.dto.NewAccountDto;
import com.danielturato.reviewapi.service.AccountService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/accounts")
public class AccountControllerImpl implements AccountController {

    private final AccountService accountService;


    public AccountControllerImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public AccountDto getAccount(String id) {
        return accountService.getAccountById(id);
    }

    @Override
    public AccountDto createAccount(@RequestBody NewAccountDto newAccountDto) {
        return accountService.createAccount(newAccountDto);
    }

    @Override
    public AccountDto updateAccount(AccountDto accountDto) {
        return null;
    }

    @Override
    public AuthResponseDto authenticate(AuthRequestDto authRequestDto) {
        return accountService.authenticate(authRequestDto);
    }
}
