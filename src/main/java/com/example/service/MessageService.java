package com.example.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Component
@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }
    
    public List<Message> getAllMessage(){
        return (List<Message>) messageRepository.findAll();
    }

    public Optional<Message> getMessageByID(int id){
        return messageRepository.findById(id);
    }

    public void deleteMessageById(int id){
        if(messageExists(id)){
            messageRepository.deleteById(id);
        }
        else{
            throw new IllegalArgumentException();
        }
        
    }

    public void updateMessage(int id, String updatedMessage){
        Message message = messageRepository.findById(id).orElseThrow();
        if(!(updatedMessage.isBlank()) || updatedMessage.length() <= 255){
            message.setMessageText(updatedMessage);
        }
        messageRepository.save(message);
        

        
    }

    public Message createMessage(Message message){
        if(message.getMessageText().isBlank() || message.getMessageText().length() > 255 || message.getPostedBy() == null || message.getMessageText() == null){
            throw new IllegalArgumentException();
        }

            Account account = accountRepository.findById(message.getPostedBy()).orElseThrow(() -> new IllegalArgumentException());
            messageRepository.save(message);
            return message;
        
        
    } 

    public List<Message> allMessageByAccount(int id){
        return messageRepository.findByPostedBy(id);
    }

    public boolean messageExists(int id){
        return messageRepository.existsById(id);
    }
}
