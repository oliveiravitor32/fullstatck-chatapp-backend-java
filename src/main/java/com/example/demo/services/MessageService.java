package com.example.demo.services;

import com.example.demo.domain.chatroom.ChatRoom;
import com.example.demo.domain.message.Message;
import com.example.demo.domain.user.User;
import com.example.demo.exceptions.CustomBadRequestException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.ChatRoomRepository;
import com.example.demo.repositories.MessageRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageService {

    @Autowired
    MessageRepository repository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChatRoomRepository chatRoomRepository;

    public Message insert(Message message) {
        if (message != null && message.getAuthor() != null && message.getChatRoom() != null) {
            User user = userRepository.findById(message.getAuthor().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found! Id: " + message.getAuthor().getId()));
            ChatRoom chatRoom = chatRoomRepository.findById(message.getChatRoom().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("ChatRoom not found! Id: " + message.getChatRoom().getId()));

            message.setTimestemp(LocalDateTime.now());
            message.setAuthor(user);
            message.setChatRoom(chatRoom);

            return repository.save(message);
        }
        else {
            throw new CustomBadRequestException("Invalid Request Data!");
        }
    }

    public Message findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Message not found! Id: " + id));
    }

    public void addLike(Long id, User user) {
        Message message = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Message not found! Id: " + id));
        User gettedUser = userRepository.findById(user.getId()).orElseThrow(() -> new ResourceNotFoundException("User not found! Id: " + message.getAuthor().getId()));
        if (!message.getUsersWhoLiked().contains(gettedUser)) {
            message.getUsersWhoLiked().add(gettedUser);
            message.setLikes(message.getUsersWhoLiked().size());
            repository.save(message);
        } else  {
            new RuntimeException("The message has already been liked by this user!");
        }
    }

    public void removeLike(Long id, User user) {
        Message message = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Message not found! Id: " + id));
        User gettedUser = userRepository.findById(user.getId()).orElseThrow(() -> new ResourceNotFoundException("User not found! Id: " + message.getAuthor().getId()));
        if (!message.getUsersWhoLiked().contains(gettedUser)) {
            new RuntimeException("This message was not liked by this user!");
        } else  {
            message.getUsersWhoLiked().remove(gettedUser);
            message.setLikes(message.getUsersWhoLiked().size());
            repository.save(message);
        }
    }
}
