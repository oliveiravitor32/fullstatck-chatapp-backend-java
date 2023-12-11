package com.example.demo.controllers;


import com.example.demo.domain.chatroom.ChatRoom;
import com.example.demo.domain.chatroom.ChatRoomWithAuthorOnMessageDTO;
import com.example.demo.domain.chatroom.ChatRoomWithoutMessagesDTO;
import com.example.demo.domain.message.Message;
import com.example.demo.domain.message.MessageWithAuthorDTO;
import com.example.demo.domain.user.AuthorDTO;
import com.example.demo.services.ChatRoomService;
import com.example.demo.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chat")
public class ChatRoomController {

    @Autowired
    private ChatRoomService service;

    @GetMapping("/rooms")
    public ResponseEntity<List<ChatRoomWithoutMessagesDTO>> getAllRooms() {
        List<ChatRoom> chats = service.findAll();
        List<ChatRoomWithoutMessagesDTO> chatsWithoutMessages = chats.stream().map(
                (room) -> new ChatRoomWithoutMessagesDTO(room.getId(), room.getName())).collect(Collectors.toList());
        return ResponseEntity.ok().body(chatsWithoutMessages);
    }

    @PostMapping("/rooms")
    public ResponseEntity<Void> createRoom(@RequestBody ChatRoom room) {
        ChatRoom chatRoom = service.insert(room);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(chatRoom.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/rooms/{id}")
    public ResponseEntity<ChatRoomWithAuthorOnMessageDTO> getRoomById(@PathVariable Long id) {
        ChatRoom chatRoom = service.findById(id);

        List<MessageWithAuthorDTO> messagesWithAuthorDTO = chatRoom.getMessages().stream().map(
                (m) -> new MessageWithAuthorDTO(m.getId(), m.getContent(), m.getTimestemp(), m.getLikes(),
                        new AuthorDTO(m.getAuthor().getId(), m.getAuthor().getName()), m.getChatRoom()))
                .collect(Collectors.toList());

        ChatRoomWithAuthorOnMessageDTO chatRoomWithAuthor =
                new ChatRoomWithAuthorOnMessageDTO(chatRoom.getId(), chatRoom.getName(), messagesWithAuthorDTO);

        return ResponseEntity.ok().body(chatRoomWithAuthor);
    }

    @GetMapping("/rooms/messages/{id}")
    public ResponseEntity<List<MessageWithAuthorDTO>> getAllMessagesByRoomId(@PathVariable Long id) {
        Set<Message> messages = service.getAllMessages(id);
        List<MessageWithAuthorDTO> messagesWithAuthorDTO = messages.stream().map(
                (m) -> new MessageWithAuthorDTO(m.getId(), m.getContent(), m.getTimestemp(), m.getLikes(),
                        new AuthorDTO(m.getAuthor().getId(), m.getAuthor().getName()), m.getChatRoom()))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(messagesWithAuthorDTO);
    }
}
