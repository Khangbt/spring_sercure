package com.example.demo.service;

import com.example.demo.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getByUser(String name);
}
