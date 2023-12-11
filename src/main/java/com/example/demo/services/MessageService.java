package com.example.demo.services;

import com.example.demo.domain.message.Message;
import com.example.demo.domain.user.User;
import com.example.demo.exceptions.NotFoundException;
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
        return repository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void addLike(Long id, User user) {
        Message message = repository.findById(id).orElseThrow(() -> new NotFoundException(id));
        if (!message.getUserWhoLiked().contains(user)) {
            message.getUserWhoLiked().add(user);
            message.setLikes(message.getUserWhoLiked().size());
            repository.save(message);
        } else  {
            new RuntimeException("The message has already been liked by this user!");
        }
    }

    public void removeLike(Long id, User user) {
        Message message = repository.findById(id).orElseThrow(() -> new NotFoundException(id));
        if (!message.getUserWhoLiked().contains(user)) {
            new RuntimeException("This message was not liked by this user!");
        } else  {
            message.getUserWhoLiked().remove(user);
            message.setLikes(message.getUserWhoLiked().size());
            repository.save(message);
        }
    }
}
