package com.example.demo.services;

import com.example.demo.domain.chatroom.ChatRoom;
import com.example.demo.repositories.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatRoomService {

    @Autowired
    ChatRoomRepository repository;

    public List<ChatRoom> findAll() {
        return repository.findAll();
    }

    public ChatRoom findById(String id) {
        Optional<ChatRoom> room = repository.findById(id);
        return room.get();
    }
}
