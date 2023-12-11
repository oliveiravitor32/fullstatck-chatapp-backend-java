package com.example.demo.domain.message;

import com.example.demo.domain.chatroom.ChatRoom;
import com.example.demo.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.Formula;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "messages")
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private LocalDateTime timestemp;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "liked_messages",
            joinColumns = @JoinColumn(name = "message_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> usersWhoLiked = new HashSet<>();

    private Integer likes = usersWhoLiked.size();

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "chatroom_id")
    private ChatRoom chatRoom;

    public Message() {}

    public Message(Long id, String content, LocalDateTime timestemp, User author, ChatRoom chatRoom) {
        this.id = id;
        this.content = content;
        this.timestemp = timestemp;
        this.author = author;
        this.chatRoom = chatRoom;
        this.likes = 0;
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Set getUsersWhoLiked() {
        return usersWhoLiked;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatroom) {
        this.chatRoom = chatroom;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        return id.equals(message.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
