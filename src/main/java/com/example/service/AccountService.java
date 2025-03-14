package com.example.service;

import com.example.entity.Account;
import com.example.exception.DuplicateUsernameException;
import com.example.exception.InvalidLoginCredentialsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.repository.AccountRepository;

import java.util.Optional;

@Service
public class AccountService {
    
    private AccountRepository accountRepository;

    @Autowired
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

    public Account userLoginAuthentication(Account account) {
        Optional<Account> optionalAccount = accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
         
        if (optionalAccount.isPresent()) {
            return optionalAccount.get();
        }

        throw new InvalidLoginCredentialsException("Account does not exist with the provided username and password.");
    } 
}
