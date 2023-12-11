package com.example.demo.services;

import com.example.demo.domain.message.Message;
import com.example.demo.domain.user.User;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.MessageRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    MessageRepository repository;

    @Autowired
    UserRepository userRepository;

    public Message insert(Message message) {
        return repository.save(message);
    }

    public Message findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Message not found! Id: " + id));
    }

    public void addLike(Long id, User user) {
        Message message = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Message not found! Id: " + id));
        User gettedUser = userRepository.findById(user.getId()).orElseThrow(() -> new ResourceNotFoundException("User not found! Id: " + message.getAuthor().getId()));
        if (!message.getUsersWhoLiked().contains(gettedUser)) {
            message.getUsersWhoLiked().add(gettedUser);
            message.setLikes(message.getUsersWhoLiked().size());
            repository.save(message);
        } else  {
            new RuntimeException("The message has already been liked by this user!");
        }
    }

    public void removeLike(Long id, User user) {
        Message message = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Message not found! Id: " + id));
        User gettedUser = userRepository.findById(user.getId()).orElseThrow(() -> new ResourceNotFoundException("User not found! Id: " + message.getAuthor().getId()));
        if (!message.getUsersWhoLiked().contains(gettedUser)) {
            new RuntimeException("This message was not liked by this user!");
        } else  {
            message.getUsersWhoLiked().remove(gettedUser);
            message.setLikes(message.getUsersWhoLiked().size());
            repository.save(message);
        }
    }
}
