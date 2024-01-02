package com.example.demo.controllers;

import com.example.demo.domain.message.Message;
import com.example.demo.domain.message.MessageWithAuthorDTO;
import com.example.demo.domain.message.SimpleMessageDTO;
import com.example.demo.domain.user.AuthorDTO;
import com.example.demo.domain.user.User;
import com.example.demo.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping(value = "/messages")
public class MessageController {

    @Autowired
    MessageService service;

    @Autowired
    WebSocketController webSocketController;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping
    public ResponseEntity<MessageWithAuthorDTO> addMessage(@RequestBody Message message) {
        message = service.insert(message);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(message.getId()).toUri();

        MessageWithAuthorDTO messageWithAuthorDTO =
                new MessageWithAuthorDTO(message.getId(), message.getContent(), message.getTimestemp(), message.getLikes(),
                        new AuthorDTO(message.getAuthor().getId(), message.getAuthor().getNickname()), message.getChatRoom());

        //Web Socket
        SimpleMessageDTO simpMessageWebSocket = new SimpleMessageDTO(message.getId(),message.getContent(),
                new AuthorDTO(message.getAuthor().getId(), message.getAuthor().getNickname()), message.getChatRoom());

        webSocketController.sendMessageToRoom(simpMessageWebSocket.chatRoom().getId(), simpMessageWebSocket);

        return ResponseEntity.created(uri).body(messageWithAuthorDTO);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<MessageWithAuthorDTO> getMessageById(@PathVariable Long id) {
        Message message = service.findById(id);
        MessageWithAuthorDTO messageWithAuthorDTO =
                new MessageWithAuthorDTO(message.getId(), message.getContent(), message.getTimestemp(),
                        message.getLikes(), new AuthorDTO(message.getAuthor().getId(), message.getAuthor().getNickname())
                        , message.getChatRoom());
        return ResponseEntity.ok().body(messageWithAuthorDTO);
    }

    @PatchMapping(value = "/{id}/like")
    public ResponseEntity<Void> addLike(@PathVariable Long id, @RequestBody User user) {
        service.addLike(id, user);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{id}/dislike")
    public ResponseEntity<Void> removeLike(@PathVariable Long id, @RequestBody User user) {
        service.removeLike(id, user);
        return ResponseEntity.noContent().build();
    }
}
