package com.example.demo.controller;

import com.example.demo.annotion.IsDelete;
import com.example.demo.annotion.IsGet;
import com.example.demo.annotion.IsPost;
import com.example.demo.annotion.IsPut;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.request.UserCreateRequest;
import com.example.demo.request.UserLoginRequest;
import com.example.demo.request.UserUpdateRequest;
import com.example.demo.service.AuthenService;
import com.example.demo.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import vn.com.itechcorp.base.api.method.BaseDtoAPIMethod;
import vn.com.itechcorp.base.api.response.APIListResponse;
import vn.com.itechcorp.base.api.response.APIResponse;
import vn.com.itechcorp.base.service.filter.BaseFilter;
import vn.com.itechcorp.base.service.filter.PaginationInfo;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/user")
public class UserController extends BaseDtoAPIMethod<UserDto, User, Long> {


    @Autowired
    private AuthenService authenService;

    private final UserService userService;

    public UserController(UserService service, UserService userService) {
        super(service);
        this.userService = userService;
    }


    @Override
    @GetMapping("/all")
    @IsGet
    public ResponseEntity<APIListResponse<List<UserDto>>> getList(@RequestParam(required = false) PaginationInfo pageInfo) {
        if (Objects.isNull(pageInfo))
            pageInfo = new PaginationInfo();
        return super.getList(pageInfo);
    }

    @PostMapping("/add")
    @IsPost
    public ResponseEntity<APIResponse<UserDto>> createAdd(@RequestBody UserCreateRequest object) {

        return userService.createCustom(object);
    }

    @Override
    @GetMapping("/{id}")
    @IsGet
    public ResponseEntity<APIResponse<UserDto>> getById(@PathVariable Long id) {
        return super.getById(id);
    }

    @Override
    public Logger getLogger() {
        return super.getLogger();
    }

    @PutMapping("/update/{id}")
    @IsPut
    public ResponseEntity<APIResponse<UserDto>> update(@RequestBody UserUpdateRequest object,@PathVariable Long id) {
        return userService.updateCustom(object);
    }

    @Override
    @IsDelete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return super.delete(id);
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
