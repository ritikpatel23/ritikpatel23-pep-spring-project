package com.example.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

public class MessageService {

    private MessageRepository messageRepository;
    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }
    
    private List<Message> message = new ArrayList<>();

    public List<Message> getAllMessage(){
        return messageRepository.findAll();
    }

    public Message getMessageByID(int id){
        return messageRepository.findById(id).orElseThrow();
    }

    public void deleteMessageById(int id){
        messageRepository.deleteById(id);
    }

    public void updateMessage(int id){
        Message message = messageRepository.getById(id);
        
    }
}
