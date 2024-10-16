package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.DuplicateUsernameException;
import com.example.repository.AccountRepository;

@Component
@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account register(Account account){
        if(account.getPassword().length() < 4 || (account.getUsername().isBlank()) || account == null){
            throw new IllegalArgumentException();
        }
        if(accountRepository.findByUsername(account.getUsername()).isPresent()){
            throw new DuplicateUsernameException("Username already exists");
        }
            return accountRepository.save(account);
        
    }

    public Account login(Account account){
        accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
        return (Account) accountRepository.findById(account.getAccountId()).orElseThrow();
    }


}
