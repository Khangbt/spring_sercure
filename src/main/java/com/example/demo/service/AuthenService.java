package com.example.demo.service;

import com.example.demo.request.UserLoginRequest;

public interface AuthenService {
    String login(UserLoginRequest userLoginDTO);
}
