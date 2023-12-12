package com.example.demo.config;

import com.example.demo.domain.chatroom.ChatRoom;
import com.example.demo.domain.message.Message;
import com.example.demo.domain.user.User;
import com.example.demo.repositories.ChatRoomRepository;
import com.example.demo.repositories.MessageRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class TestConfig implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public void run(String... args) throws Exception {
        userRepository.deleteAll();
        chatRoomRepository.deleteAll();
        messageRepository.deleteAll();

        User user1 = new User(null, "Luna", "151516");
        User user2 = new User(null, "Rachel", "23xza6");

        userRepository.saveAll(Arrays.asList(user1, user2));

        ChatRoom room1 = new ChatRoom(null, "Cotidiano");
        ChatRoom room2 = new ChatRoom(null, "Histórias");

        chatRoomRepository.saveAll(Arrays.asList(room1, room2));
        //    Repository.saveAll(Arrays.asList());
        Set<User> likes = new HashSet<>();
        likes.add(user1);

        Message msg1 = new Message(null, "Tomando café....", LocalDateTime.now(), user1, room1);
        Message msg2 = new Message(null, "Alguém ja ouviu aquela dos três porquinhos?", LocalDateTime.now(), user2, room2);
        Message msg3 = new Message(null, "Acordei!", LocalDateTime.now(), user2, room1);
        Message msg4 = new Message(null, "Já ouvi sim.", LocalDateTime.now(), user1, room2);

        //msg2.getUsersWhoLiked().add(user1);
        //msg3.getUsersWhoLiked().add(user1);
        messageRepository.saveAll(Arrays.asList(msg1, msg2, msg3, msg4));

    }
}
