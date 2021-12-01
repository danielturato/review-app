package com.danielturato.reviewapi.service;

import com.danielturato.reviewapi.dto.AccountDto;
import com.danielturato.reviewapi.dto.NewAccountDto;

public interface AccountService {
    AccountDto getAccountById(String id);

    AccountDto createAccount(NewAccountDto newAccountDto);
}
