package com.danielturato.reviewapi.repository;

import com.danielturato.reviewapi.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String> {
}
