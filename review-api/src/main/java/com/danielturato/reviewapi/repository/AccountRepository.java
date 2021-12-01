package com.danielturato.reviewapi.repository;

import com.danielturato.reviewapi.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface AccountRepository extends MongoRepository<Account, String> {

    @Query("{email: '?0'}")
    Optional<Account> findAccountByEmail(String email);
}
