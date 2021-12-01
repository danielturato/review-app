package com.danielturato.reviewapi.service;

import com.danielturato.reviewapi.core.MapStructMapper;
import com.danielturato.reviewapi.dto.AccountDto;
import com.danielturato.reviewapi.dto.NewAccountDto;
import com.danielturato.reviewapi.model.Account;
import com.danielturato.reviewapi.repository.AccountRepository;

import java.util.Optional;

public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final MapStructMapper mapper;

    public AccountServiceImpl(AccountRepository accountRepository, MapStructMapper mapper) {
        this.accountRepository = accountRepository;
        this.mapper = mapper;
    }

    @Override
    public AccountDto getAccountById(String id) {
        Optional<Account> account = accountRepository.findById(id);

        if (account.isPresent()) {
            return mapper.accountToAccountDto(account.get());
        }

        throw new RuntimeException("No Account with id: " + id);
    }

    @Override
    public AccountDto createAccount(NewAccountDto newAccountDto) {
        Account account = mapper.newAccountToAccount(newAccountDto);
        //TODO:drt - hash pwd
        account = accountRepository.save(account);

        return mapper.accountToAccountDto(account);
    }
}
