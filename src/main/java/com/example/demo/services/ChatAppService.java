package com.example.demo.services;

import com.example.demo.domain.chatroom.ChatRoom;
import com.example.demo.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatAppService {

    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    @Autowired
    ChatRoomService chatRoomService;


    public List<ChatRoom> getAllRooms() {
        return chatRoomService.findAll();
    }

    public ChatRoom findRoomById(String id) {
        return chatRoomService.findById(id);
    }

    public List<User> getUser() {
        return userService.findAll();
    }
}
