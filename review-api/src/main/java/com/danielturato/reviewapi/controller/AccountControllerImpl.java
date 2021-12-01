package com.danielturato.reviewapi.controller;

import com.danielturato.reviewapi.dto.AccountDto;
import com.danielturato.reviewapi.dto.NewAccountDto;
import com.danielturato.reviewapi.service.AccountService;

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
    public AccountDto createAccount(NewAccountDto newAccountDto) {
        return accountService.createAccount(newAccountDto);
    }
}
