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
    

    public List<Message> getAllMessage(){
        return (List<Message>) messageRepository.findAll();
    }

    public Message getMessageByID(int id){
        return messageRepository.findById(id).orElseThrow();
    }

    public void deleteMessageById(int id){
        messageRepository.deleteById(id);
    }

    public void updateMessage(int id, String updatedMessage){
        Message message = messageRepository.getById(id);
        if(!(updatedMessage.isBlank()) || updatedMessage.length() <= 255){
            message.setMessageText(updatedMessage);
        }
        messageRepository.save(message);

        
    }

    public void createMessage(Message message){
        messageRepository.save(message);
    } 

    public List<Message> allMessageByAccount(int id){
        return (List<Message>) messageRepository.findById(id).orElseThrow();
    }
}
