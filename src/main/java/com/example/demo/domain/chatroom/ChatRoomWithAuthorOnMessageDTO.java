package com.example.demo.domain.chatroom;

import com.example.demo.domain.message.MessageWithAuthorDTO;

import java.util.List;

public record ChatRoomWithAuthorOnMessageDTO(Long id, String name, List<MessageWithAuthorDTO> messages) {
}
