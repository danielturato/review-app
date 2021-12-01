package com.danielturato.reviewapi.core;

import com.danielturato.reviewapi.dto.AccountDto;
import com.danielturato.reviewapi.dto.NewAccountDto;
import com.danielturato.reviewapi.model.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    AccountDto accountToAccountDto(Account account);

    Account newAccountToAccount(NewAccountDto newAccountDto);
}
