package com.danielturato.reviewapi.core;

import com.danielturato.reviewapi.dto.AccountDto;
import com.danielturato.reviewapi.dto.NewAccountDto;
import com.danielturato.reviewapi.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    AccountDto accountToAccountDto(Account account);

    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    Account newAccountToAccount(NewAccountDto newAccountDto);
}
