package com.example.controller;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import java.util.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@Controller
@ComponentScan(basePackages = "com.revature.components")
@Component
public class SocialMediaController {

    
    
    private AccountService accountService;
    private MessageService messageService;

    
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    //create message
    @PostMapping("messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message){
        Message createdMessage = messageService.createMessage(message);
        if(createdMessage == null){
            return (ResponseEntity<Message>) ResponseEntity.status(HttpStatus.UNAUTHORIZED);
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).body(messageService.createMessage(message));
        }
        
    }
    
    
    // get all messages

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        return ResponseEntity.status(200).body(messageService.getAllMessage());
    }

    // get message by id
    @GetMapping("messages/{messageId}")
    public ResponseEntity<Message> getMessageByID(@PathVariable int messageId){
        Message message = messageService.getMessageByID(messageId);
        if (message == null){
            return (ResponseEntity<Message>) ResponseEntity.ok();
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).body(message);
        }
        
    }

    // delete message by id
    @DeleteMapping("messages/{id}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable int id){
       if(messageService.messageExists(id) == false){
        return  (ResponseEntity<Integer>) ResponseEntity.status(HttpStatus.OK);
       } 
       else{
        messageService.deleteMessageById(id);
        return ResponseEntity.status(HttpStatus.OK).body(1);
       }

    }

    // update message text
    @PatchMapping("messages/{messageId}")
    public ResponseEntity<Integer> updateMessage(@RequestParam int messageId, @RequestParam String messageText){
        messageService.updateMessage(messageId, messageText );
        return ResponseEntity.status(HttpStatus.OK).body(1);
    }

    // get all messages using accountId
    @GetMapping("accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesFromAccount(@PathVariable int accountId){
        return ResponseEntity.status(HttpStatus.OK).body(messageService.allMessageByAccount(accountId));
    }

    // register account
    @PostMapping("/register")
    public @ResponseBody ResponseEntity<Account> registerAccount(@RequestBody Account account){
        Account addedAccount = accountService.register(account);
        if(addedAccount == null){
            return (ResponseEntity<Account>) ResponseEntity.status(HttpStatus.CONFLICT);
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).body(account);
        }
        
    }

    // login
    @PostMapping("/login")
    public @ResponseBody ResponseEntity<Account> loginAccount(@RequestBody Account account){
        accountService.login(account);
        return ResponseEntity.status(HttpStatus.OK).body(account);
    }


}
