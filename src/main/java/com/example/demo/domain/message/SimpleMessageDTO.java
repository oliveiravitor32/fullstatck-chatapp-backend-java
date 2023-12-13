package com.example.demo.domain.message;

import com.example.demo.domain.chatroom.ChatRoom;
import com.example.demo.domain.user.AuthorDTO;

public record SimpleMessageDTO(Long id, String content, AuthorDTO author, ChatRoom chatRoom) {
}
