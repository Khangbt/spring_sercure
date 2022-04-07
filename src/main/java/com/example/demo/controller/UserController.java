package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.request.UserCreateRequest;
import com.example.demo.request.UserLoginRequest;
import com.example.demo.service.AuthenService;
import com.example.demo.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.itechcorp.base.api.method.BaseDtoAPIMethod;
import vn.com.itechcorp.base.api.response.APIListResponse;
import vn.com.itechcorp.base.api.response.APIResponse;
import vn.com.itechcorp.base.service.BaseDtoService;
import vn.com.itechcorp.base.service.dto.DtoUpdate;
import vn.com.itechcorp.base.service.filter.BaseFilter;
import vn.com.itechcorp.base.service.filter.PaginationInfo;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController extends BaseDtoAPIMethod<UserDto, User, Long> {

    @Autowired
    private UserServiceImpl service;
    @Autowired
    private AuthenService authenService;

    public UserController(UserServiceImpl service) {
        super(service);
    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<APIListResponse<List<UserDto>>> getList(@RequestParam(required = false) PaginationInfo pageInfo) {
        if (Objects.isNull(pageInfo))
            pageInfo = new PaginationInfo();
        return super.getList(pageInfo);
    }

    @PostMapping("/add")
    public ResponseEntity<APIResponse<UserDto>> create(@RequestBody UserCreateRequest object) {
        return super.create(object);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<UserDto>> getById(@PathVariable Long id) {
        return super.getById(id);
    }

    @Override
    public Logger getLogger() {
        return super.getLogger();
    }

    @Override
    public BaseDtoService<UserDto, User, Long> getService() {
        return super.getService();
    }

    @Override
    public ResponseEntity<APIResponse<UserDto>> update(DtoUpdate<User, Long> object) {
        return super.update(object);
    }

    @Override
    public ResponseEntity<Object> delete(Long aLong) {
        return super.delete(aLong);
    }

    @Override
    public ResponseEntity<APIListResponse<List<UserDto>>> search(BaseFilter<User> filter, PaginationInfo pageInfo) {
        return super.search(filter, pageInfo);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest userLoginDTO) {
        try {
            String token = authenService.login(userLoginDTO);
            if (Objects.isNull(token))
                return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }
}
