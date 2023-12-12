package com.example.demo.services;

import com.example.demo.domain.chatroom.ChatRoom;
import com.example.demo.domain.message.Message;
import com.example.demo.exceptions.CustomBadRequestException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

@Service
public class ChatRoomService {

    @Autowired
    ChatRoomRepository repository;

    public List<ChatRoom> findAll() {
        return repository.findAll();
    }

    public ChatRoom findById(Long id) {
        return repository.findById(id).orElseThrow( () -> new ResourceNotFoundException("ChatRoom not found! Id: " + id));
    }

    public ChatRoom insert(ChatRoom room) {
        if (room.getName() != null) {
            return repository.save(room);
        } else {
            throw new CustomBadRequestException("Invalid Request Data!");
        }
    }

    public Set<Message> getAllMessages(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ChatRoom not found! Id: " + id)).getMessages();
    }
}
