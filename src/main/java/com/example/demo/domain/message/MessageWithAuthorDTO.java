package com.example.demo.domain.message;

import com.example.demo.domain.chatroom.ChatRoom;
import com.example.demo.domain.user.AuthorDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

public class MessageWithAuthorDTO {

    @Id
    private Long id;

    private String content;

    private LocalDateTime timestemp;

    private Integer likes;

    private AuthorDTO author;

    @JsonIgnore
    private ChatRoom chatRoom;

    public MessageWithAuthorDTO() {}

    public MessageWithAuthorDTO(Message message) {
        this.id = message.getId();
        this.content = message.getContent();
        this.timestemp = message.getTimestemp();
        this.likes = message.getLikes();
        this.author = new AuthorDTO(message.getAuthor().getId(), message.getAuthor().getName());
        this.chatRoom = message.getChatRoom();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestemp() {
        return timestemp;
    }

    public void setTimestemp(LocalDateTime timestemp) {
        this.timestemp = timestemp;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public AuthorDTO getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDTO author) {
        this.author = author;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }
}
