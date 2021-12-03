package com.danielturato.reviewapi.service;

import com.danielturato.reviewapi.core.MapStructMapper;
import com.danielturato.reviewapi.dto.AccountDto;
import com.danielturato.reviewapi.dto.AuthRequestDto;
import com.danielturato.reviewapi.dto.AuthResponseDto;
import com.danielturato.reviewapi.dto.NewAccountDto;
import com.danielturato.reviewapi.model.Account;
import com.danielturato.reviewapi.repository.AccountRepository;
import com.danielturato.reviewapi.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final MapStructMapper mapper;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    public AccountServiceImpl(AccountRepository accountRepository, MapStructMapper mapper, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.accountRepository = accountRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
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
    public Account getRawAccountById(String id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));
        // TODO:drt - fix
    }

    @Override
    public AccountDto createAccount(NewAccountDto newAccountDto) {
        Account account = mapper.newAccountToAccount(newAccountDto);

        if (accountRepository.findAccountByEmail(account.getEmail()).isPresent()) {
            // TODO:drt - do exception
            throw new RuntimeException("Account already exists");
        }

        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account = accountRepository.save(account);

        return mapper.accountToAccountDto(account);
    }

    @Override
    public AuthResponseDto authenticate(AuthRequestDto authRequestDto) {
        Optional<Account> optAccount = accountRepository.findAccountByEmail(authRequestDto.getEmail());

        if (optAccount.isEmpty()) {
            //TODO:drt - exc
            throw new RuntimeException("no email exists");
        }

        Account account = optAccount.get();
        if (passwordEncoder.matches(authRequestDto.getPassword(), account.getPassword())) {
            Map<String, String> claims = new HashMap<>();
            claims.put("email", account.getEmail());
            try {
                String jwt = jwtUtil.createJwtToken(claims, account.getId());

                return new AuthResponseDto(jwt);
            } catch (Exception ex) {
                //TODO:drt do
            }
        }

        //TODO:drt - exc
        throw new RuntimeException("incorrect password");
    }


}
