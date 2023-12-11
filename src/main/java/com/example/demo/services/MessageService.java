package com.example.demo.services;

import com.example.demo.domain.message.Message;
import com.example.demo.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    MessageRepository repository;

    public Message insert(Message message) {
        return repository.save(message);
    }

    public Message findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Message not found! Id: " + id));
    }
}
