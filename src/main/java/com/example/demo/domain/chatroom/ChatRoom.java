package com.example.demo.domain.chatroom;

import com.example.demo.domain.message.Message;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "chatrooms")
public class ChatRoom implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private Set<Message> messages = new HashSet<>();

    public ChatRoom() {}
    public ChatRoom(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatRoom chatRoom = (ChatRoom) o;

        return id.equals(chatRoom.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
