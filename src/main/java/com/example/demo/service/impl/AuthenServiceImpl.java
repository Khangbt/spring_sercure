package com.example.demo.service.impl;

import com.example.demo.config.jwt.JWTProvider;
import com.example.demo.entity.User;
import com.example.demo.request.UserLoginRequest;
import com.example.demo.service.AuthenService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenServiceImpl implements AuthenService {


    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;

    @Override
    public String login(UserLoginRequest userLoginDTO) {
        try {
            UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(userLoginDTO.getUser(), userLoginDTO.getPass());
            Authentication authentication = authenticationManager.authenticate(upToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Optional<User> user = userService.getByUser(userLoginDTO.getUser());
            if (!user.isPresent())
                throw new Exception();
            return jwtProvider.generateToken(user.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
