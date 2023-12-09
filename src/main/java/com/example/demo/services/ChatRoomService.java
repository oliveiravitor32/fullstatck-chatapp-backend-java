package com.example.demo.services;

import com.example.demo.domain.chatroom.ChatRoom;
import com.example.demo.domain.message.Message;
import com.example.demo.repositories.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ChatRoomService {

    @Autowired
    ChatRoomRepository repository;

    public List<ChatRoom> findAll() {
        return repository.findAll();
    }

    public ChatRoom findById(Long id) {
        Optional<ChatRoom> room = repository.findById(id);
        return room.get();
    }

    public ChatRoom insert(ChatRoom room) {
        return repository.save(room);
    }

    public List<Message> getAllMessages(Long id) {
        Optional<ChatRoom> chatRoom = repository.findById(id);
        if (chatRoom.isPresent()) {
            return chatRoom.get().getMessages();
        } else {
            return Collections.emptyList();
        }
    }
}
