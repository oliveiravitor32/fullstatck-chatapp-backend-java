package com.example.demo.controllers;

import com.example.demo.domain.message.Message;
import com.example.demo.domain.message.MessageWithAuthorDTO;
import com.example.demo.domain.user.AuthorDTO;
import com.example.demo.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    MessageService service;

    @PostMapping
    public ResponseEntity<Void> addMessage(@RequestBody Message message) {
        Message msg = service.insert(message);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(msg.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageWithAuthorDTO> getMessageById(@PathVariable Long id) {
        Message message = service.findById(id);
        MessageWithAuthorDTO messageWithAuthorDTO =
                new MessageWithAuthorDTO(message.getId(), message.getContent(), message.getTimestemp(),
                        message.getLikes(),new AuthorDTO(message.getAuthor().getId(), message.getAuthor().getName())
                        , message.getChatRoom());
        return ResponseEntity.ok().body(messageWithAuthorDTO);
    }
}
