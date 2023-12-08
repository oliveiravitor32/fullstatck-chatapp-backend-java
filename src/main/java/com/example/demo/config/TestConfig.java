package com.example.demo.config;

import com.example.demo.domain.user.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.ChatAppService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class TestConfig implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        User user1 = new User(null, "Luna", "151516");
        User user2 = new User(null, "Rachel", "23xza6");

        userRepository.saveAll(Arrays.asList(user1, user2));
    }
}
