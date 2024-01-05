package com.example.demo.controllers;

import com.auth0.jwt.JWT;
import com.example.demo.domain.chatroom.ChatRoom;
import com.example.demo.domain.message.Message;
import com.example.demo.domain.message.MessageWithAuthorDTO;
import com.example.demo.domain.message.SimpleMessageDTO;
import com.example.demo.domain.user.AuthorDTO;
import com.example.demo.domain.user.User;
import com.example.demo.infra.security.TokenService;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.AuthorizationService;
import com.example.demo.services.ChatRoomService;
import com.example.demo.services.MessageService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@CrossOrigin(origins = {"http://localhost:4200", "https://fullstack-chatapp-frontend-angular.vercel.app"})
@RestController
@RequestMapping(value = "/messages")
public class MessageController {

    @Autowired
    MessageService service;

    @Autowired
    ChatRoomService chatRoomService;

    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

    @Autowired
    WebSocketController webSocketController;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping
    public ResponseEntity<MessageWithAuthorDTO> addMessage(@RequestHeader("Authorization") String token, @RequestBody SimpleMessageDTO sendMessage) {

        System.out.println(sendMessage.authorNickname());

        Long userId = tokenService.getUserId(token);

        System.out.println(userId);

        ChatRoom chatRoom = chatRoomService.findById(sendMessage.chatRoomId());
        User user = userService.findById(userId);

        System.out.println(user.getNickname());

        if(!user.getNickname().equals(sendMessage.authorNickname())) {
            throw new RuntimeException("Token != User");
        }

        String content = sendMessage.content();
        Message message = new Message(content, user, chatRoom);
        message = service.insert(message);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(message.getId()).toUri();

        MessageWithAuthorDTO messageWithAuthorDTO =
                new MessageWithAuthorDTO(message.getId(), message.getContent(), message.getTimestemp(), message.getLikes(),
                        new AuthorDTO(message.getAuthor().getId(), message.getAuthor().getNickname()), message.getChatRoom());

        //Web Socket
        SimpleMessageDTO simpMessageWebSocket = new SimpleMessageDTO(message.getContent(),
                message.getAuthor().getNickname(), message.getChatRoom().getId());

        webSocketController.sendMessageToRoom(simpMessageWebSocket.chatRoomId(), simpMessageWebSocket);

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
