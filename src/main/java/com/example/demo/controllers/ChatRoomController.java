package com.example.demo.controllers;


import com.example.demo.domain.chatroom.ChatRoom;
import com.example.demo.domain.chatroom.ChatRoomWithoutMessagesDTO;
import com.example.demo.domain.message.Message;
import com.example.demo.domain.message.MessageWithAuthorDTO;
import com.example.demo.domain.user.AuthorDTO;
import com.example.demo.services.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/chatroom")
public class ChatRoomController {

    @Autowired
    private ChatRoomService service;

    @GetMapping(value = "/rooms")
    public ResponseEntity<List<ChatRoomWithoutMessagesDTO>> getAllRooms() {
        List<ChatRoom> chats = service.findAll();
        List<ChatRoomWithoutMessagesDTO> chatsWithoutMessages = chats.stream().map(
                (room) -> new ChatRoomWithoutMessagesDTO(room.getId(), room.getName())).collect(Collectors.toList());
        return ResponseEntity.ok().body(chatsWithoutMessages);
    }

    @PostMapping
    public ResponseEntity<ChatRoom> createRoom(@RequestBody ChatRoom room) {
        ChatRoom chatRoom = service.insert(room);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(chatRoom.getId()).toUri();
        return ResponseEntity.created(uri).body(chatRoom);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ChatRoomWithoutMessagesDTO> getRoomById(@PathVariable Long id) {
        ChatRoom chatRoom = service.findById(id);
        ChatRoomWithoutMessagesDTO chatRoomWithoutMessagesDTO = new ChatRoomWithoutMessagesDTO(chatRoom.getId(), chatRoom.getName());

        return ResponseEntity.ok().body(chatRoomWithoutMessagesDTO);
    }

    @GetMapping(value = "/{id}/messages")
    public ResponseEntity<Set<MessageWithAuthorDTO>> getAllMessagesByRoomId(@PathVariable Long id) {
        Set<Message> messages = service.getAllMessages(id);
        Set<MessageWithAuthorDTO> messagesWithAuthorDTO = messages.stream().map(
                (m) -> new MessageWithAuthorDTO(m.getId(), m.getContent(), m.getTimestemp(), m.getLikes(),
                        new AuthorDTO(m.getAuthor().getId(), m.getAuthor().getUsername()), m.getChatRoom()))
                .collect(Collectors.toSet());
        return ResponseEntity.ok().body(messagesWithAuthorDTO);
    }
}
