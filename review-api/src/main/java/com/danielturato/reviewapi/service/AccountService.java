package com.danielturato.reviewapi.service;

import com.danielturato.reviewapi.dto.AccountDto;
import com.danielturato.reviewapi.dto.AuthRequestDto;
import com.danielturato.reviewapi.dto.AuthResponseDto;
import com.danielturato.reviewapi.dto.NewAccountDto;
import com.danielturato.reviewapi.model.Account;

public interface AccountService {
    AccountDto getAccountById(String id);

    Account getRawAccountById(String id);

    AccountDto createAccount(NewAccountDto newAccountDto);

    AuthResponseDto authenticate(AuthRequestDto authRequestDto);
}
