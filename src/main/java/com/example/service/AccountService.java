package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

public class AccountService {

    @Autowired
    private MessageService messageService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(MessageService messageService, AccountService accountService, AccountRepository accountRepository){
        this.accountService = accountService;
        this.messageService = messageService;
        this.accountRepository = accountRepository;
    }

    public void register(Account account){
        if(account.getPassword().length() < 4 || !(account.getUsername().isBlank())){
        accountRepository.save(account);
        }
    }

    public Account login(Account account){
        accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
        return (Account) accountRepository.findById(account.getAccountId()).orElseThrow();
    }


}
