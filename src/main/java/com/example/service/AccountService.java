package com.example.service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.DuplicateUsernameException;

import org.springframework.stereotype.Service;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(Account account) {
        if (account.getPassword().length() < 4 || account.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username or password is invalid.");
        }
        
        if (accountRepository.findByUsername(account.getUsername()).isPresent()) {
            throw new DuplicateUsernameException("The username '" + account.getUsername() + "' is taken");
        }

        return accountRepository.save(account);
    }
}
