package com.example.demo.controllers;

import com.example.demo.domain.message.Message;
import com.example.demo.domain.message.SimpleMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/{roomId}")
    public void sendMessageToRoom(@DestinationVariable Long roomId, SimpleMessageDTO message) {
        messagingTemplate.convertAndSend("/chat/" + roomId, message);
    }
}
