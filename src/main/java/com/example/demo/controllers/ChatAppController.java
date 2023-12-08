package com.example.demo.controllers;

import com.example.demo.domain.chatroom.ChatRoom;
import com.example.demo.domain.user.User;
import com.example.demo.services.ChatAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chatapp")
public class ChatAppController {

    @Autowired
    ChatAppService service;

    @GetMapping(value = "/chats")
    public ResponseEntity<List<ChatRoom>> getAllRooms() {
        List<ChatRoom> chats = service.getAllRooms();
        return new ResponseEntity<>(chats, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ChatRoom> findRoomById(@PathVariable String id) {
        ChatRoom room = service.findRoomById(id);
        return ResponseEntity.ok().body(room);
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = service.getUser();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
