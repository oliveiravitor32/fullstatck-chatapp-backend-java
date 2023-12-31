package com.example.demo.domain.message;

import com.example.demo.domain.chatroom.ChatRoom;
import com.example.demo.domain.user.AuthorDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;


public record MessageWithAuthorDTO(Long id, String content, LocalDateTime timestemp, Integer likes, AuthorDTO author, ChatRoom chatRoom) {

    @JsonIgnore
    public ChatRoom chatRoom() {
        return chatRoom;
    }
}
