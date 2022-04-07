package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.request.UserCreateRequest;
import com.example.demo.request.UserUpdateRequest;
import org.springframework.http.ResponseEntity;
import vn.com.itechcorp.base.api.response.APIResponse;
import vn.com.itechcorp.base.service.AuditableDtoService;

import java.util.Optional;

public interface UserService extends AuditableDtoService<UserDto, User, Long> {
    Optional<User> getByUser(String name);

    ResponseEntity<APIResponse<UserDto>> createCustom(UserCreateRequest userCreateRequest);

    ResponseEntity<APIResponse<UserDto>> updateCustom(UserUpdateRequest userUpdateRequest);
}
