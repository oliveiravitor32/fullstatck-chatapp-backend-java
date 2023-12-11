package com.example.demo.domain.chatroom;

import com.example.demo.domain.message.MessageWithAuthorDTO;

import java.util.Set;

public record ChatRoomWithAuthorOnMessageDTO(Long id, String name, Set<MessageWithAuthorDTO> messages) {
}
