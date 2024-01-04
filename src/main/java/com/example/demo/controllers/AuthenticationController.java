package com.example.demo.controllers;

import com.example.demo.domain.user.*;
import com.example.demo.exceptions.AuthenticationIncorrectLogin;
import com.example.demo.exceptions.AuthenticationUserAlreadyExistsException;
import com.example.demo.infra.security.TokenService;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:4200", "https://fullstack-chatapp-frontend-angular.vercel.app"})
@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO user){
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(user.nickname(), user.password());
        try {
            Authentication auth = authenticationManager.authenticate(usernamePassword);
            String token = tokenService.generateToken((User) auth.getPrincipal());
            return ResponseEntity.ok().body(new LoginResponseDTO(token, user.nickname()));
        } catch (AuthenticationException e) {
            throw new AuthenticationIncorrectLogin("Incorrect login!");
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDTO user) {
        if (repository.findByNickname(user.nickname()) != null) {
            throw new AuthenticationUserAlreadyExistsException("Nickname already exists!");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(user.password());
        User newUser = new User(user.nickname(), encryptedPassword, UserRole.USER);

        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
