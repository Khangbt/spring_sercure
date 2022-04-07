package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.exception.CustomExceptionHandler;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
//    @Transactional // <-- added
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userService.getByUser(s);
        if (!user.isPresent()) {
            throw new CustomExceptionHandler(" Không tồn tại", HttpStatus.UNAUTHORIZED);
        }
        List<GrantedAuthority> roleList = new ArrayList<>();
        user.get().getRoleList().forEach(role -> {
            roleList.add(new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase()));
        });
        user.get().setListRole(roleList);
        return new org.springframework.security.core.userdetails.User(user.get().getName(), user.get().getPassWork(), user.get().getListRole());

    }
}
