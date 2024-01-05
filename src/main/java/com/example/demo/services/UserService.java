package com.example.demo.services;


import com.example.demo.domain.chatroom.ChatRoom;
import com.example.demo.domain.user.User;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User findById(Long id) {
        return repository.findById(id).orElseThrow( () -> new ResourceNotFoundException("User not found! Id: " + id));
    }
}