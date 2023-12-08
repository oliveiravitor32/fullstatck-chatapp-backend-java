package com.example.demo.domain.chatroom;

import com.example.demo.domain.message.Message;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chatrooms")
public class ChatRoom implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    List<Message> messages = new ArrayList<>();

    public ChatRoom() {}
    public ChatRoom(Long id, String name) {
        this.id = id;
        this.name = name;
        this.messages = new ArrayList<>();
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

    public List<Message> getMessages() {
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
