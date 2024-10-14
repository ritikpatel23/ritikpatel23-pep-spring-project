package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController(AccountService accountService){
        this.accountService = accountService;
    }

    public SocialMediaController(MessageService messageService){
        this.messageService = messageService;
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Message> getAllMessage(){
        return messageService.getAllMessage();
    }

    @GetMapping(params = "id")
    public @ResponseBody ResponseEntity<Message> getMessageByID(@RequestParam int id){
        return new ResponseEntity<>(messageService.getMessageByID(id), HttpStatus.OK);
    }


}
