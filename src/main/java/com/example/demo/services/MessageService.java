package com.example.demo.services;

import com.example.demo.domain.message.Message;
import com.example.demo.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    MessageRepository repository;

    public Message insert(Message message) {
        return repository.save(message);
    }

    public Message findById(Long id) {
        Optional<Message> message = repository.findById(id);
        if (message.isPresent()) {
            return message.get();
        } else {
            return null;
        }
    }
}
