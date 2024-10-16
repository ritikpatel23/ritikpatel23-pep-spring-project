package com.example.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Component
@Service
public class MessageService {

    @Autowired
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
        Message message = messageRepository.findById(id).orElseThrow();
        if(!(updatedMessage.isBlank()) || updatedMessage.length() <= 255){
            message.setMessageText(updatedMessage);
        }
        messageRepository.save(message);
        

        
    }

    public Message createMessage(Message message){
        if(message.getMessageText().isBlank() || message.getMessageText().length() > 255){
            return null;
        }
        else{
            messageRepository.save(message);
            return message;
        }
        
    } 

    public List<Message> allMessageByAccount(int id){
        return messageRepository.findByPostedBy(id);
    }

    public boolean messageExists(int id){
        return messageRepository.existsById(id);
    }
}
