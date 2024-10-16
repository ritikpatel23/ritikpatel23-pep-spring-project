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
import com.example.exception.DuplicateUsernameException;

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
        try{
        Message createdMessage = messageService.createMessage(message);
        return ResponseEntity.ok(createdMessage);
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
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
        Optional<Message> message = messageService.getMessageByID(messageId);
        return ResponseEntity.ok(message.orElse(null));
        
    }

    // delete message by id
    @DeleteMapping("messages/{id}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable int id){
        try{
            messageService.deleteMessageById(id);
        return ResponseEntity.ok(1);
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.ok().build();
        }
        
       }

    

    // update message text
    @PatchMapping("messages/{messageId}")
    public ResponseEntity<Integer> updateMessage(@PathVariable int messageId, @RequestBody Map<String, String> updatedText){
        String updatedMessageText = updatedText.get("messageText");
        try{
            messageService.updateMessage(messageId, updatedMessageText);
            return ResponseEntity.ok(1);
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
        
        
    }

    // get all messages using accountId
    @GetMapping("accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesFromAccount(@PathVariable int accountId){
        return ResponseEntity.status(HttpStatus.OK).body(messageService.allMessageByAccount(accountId));
    }

    // register account
    @PostMapping("register")
    public @ResponseBody ResponseEntity<Account> registerAccount(@RequestBody Account account){
        try{
            Account createdAccount = accountService.register(account);
            return ResponseEntity.ok(createdAccount);
        }
            catch(DuplicateUsernameException e){
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            catch(IllegalArgumentException e){
                return ResponseEntity.badRequest().build();
            }
        
    }

    // login
    @PostMapping("login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account account){
        try{
            Account loginAccount = accountService.login(account.getUsername(), account.getPassword());
            return ResponseEntity.ok(loginAccount);
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


}
