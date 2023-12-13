package com.example.demo.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> handleObjectNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        String error = "Resource not found";
        HttpStatus status =  HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(LocalDateTime.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(err.getStatus()).body(err);
    }

    @ExceptionHandler(CustomBadRequestException.class)
    public ResponseEntity<StandardError> handlePostBadRequest(CustomBadRequestException e, HttpServletRequest request) {
        String error = "Invalid Request Data";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(LocalDateTime.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(err.getStatus()).body(err);
    }

    @ExceptionHandler(DuplicateLikeException.class)
    public ResponseEntity<StandardError> handleDuplicateLike(DuplicateLikeException e, HttpServletRequest request) {
        String error = "You already liked this message!";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(LocalDateTime.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(err.getStatus()).body(err);
    }

    @ExceptionHandler(LikeNotFoundException.class)
    public ResponseEntity<StandardError> handleLikeNotFound(LikeNotFoundException e, HttpServletRequest request) {
        String error = "You are trying to remove a 'like' from a message you didn't like!";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(LocalDateTime.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(err.getStatus()).body(err);
    }

    @ExceptionHandler(AuthenticationInvalidTokenException.class)
    public ResponseEntity<StandardError> handleInvalidToken(AuthenticationInvalidTokenException e, HttpServletRequest request) {
        String error = "The entered token is invalid!";
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        StandardError err = new StandardError(LocalDateTime.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(err.getStatus()).body(err);
    }

    @ExceptionHandler(AuthenticationUserAlreadyExistsException.class)
    public ResponseEntity<StandardError> handleResgisterUserNicknameAlredyExists(AuthenticationUserAlreadyExistsException e, HttpServletRequest request) {
        String error = "The nickname you entered already exists!";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(LocalDateTime.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(err.getStatus()).body(err);
    }

    @ExceptionHandler(AuthenticationIncorrectLogin.class)
    public ResponseEntity<StandardError> handleIncorrectLogin(AuthenticationIncorrectLogin e, HttpServletRequest request) {
        String error = "The nickname or password is invalid!";
        HttpStatus status = HttpStatus.FORBIDDEN;
        StandardError err = new StandardError(LocalDateTime.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(err.getStatus()).body(err);
    }
}
