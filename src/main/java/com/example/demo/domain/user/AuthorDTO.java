package com.example.demo.domain.user;

import jakarta.persistence.Id;

import java.io.Serializable;

public record AuthorDTO(Long id, String name) {

}
