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
@RequestMapping("SocialMedia")
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
    @PostMapping("create")
    public ResponseEntity<Message> createMessage(@RequestBody Message message){
        return ResponseEntity.status(HttpStatus.OK).body(messageService.createMessage(message));
    }
    
    
    // get all messages
    @GetMapping
    public List<Message> getAllMessages(){
        return (List<Message>) ResponseEntity.status(200).body(messageService.getAllMessage());
    }

    // get message by id
    @GetMapping(params = "id")
    public ResponseEntity<Message> getMessageByID(@RequestParam int id){
        return new ResponseEntity<>(messageService.getMessageByID(id), HttpStatus.OK);
    }

    // delete message by id
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable int id){
       if(getMessageByID(id) == null){
        return  (ResponseEntity<Integer>) ResponseEntity.status(HttpStatus.OK);
       } 
       else{
        messageService.deleteMessageById(id);
        return ResponseEntity.status(HttpStatus.OK).body(1);
       }

    }

    // update message text
    @PatchMapping("update")
    public ResponseEntity<Integer> updateMessage(@RequestParam int messageId, @RequestBody String messageText){
        messageService.updateMessage(messageId, messageText );
        return ResponseEntity.status(HttpStatus.OK).body(1);
    }

    // get all messages using accountId
    @GetMapping(params = "accountId")
    public ResponseEntity<List<Message>> getAllMessagesFromAccount(@RequestBody int accountId){
        return ResponseEntity.status(HttpStatus.OK).body(messageService.allMessageByAccount(accountId));
    }

    // register account
    @PostMapping("register")
    public @ResponseBody ResponseEntity<Account> registerAccount(@RequestBody Account account){
        accountService.register(account);
        return ResponseEntity.status(HttpStatus.OK).body(account);
    }

    // login
    @PostMapping("login")
    public @ResponseBody ResponseEntity<Account> loginAccount(@RequestBody Account account){
        accountService.login(account);
        return ResponseEntity.status(HttpStatus.OK).body(account);
    }


}
