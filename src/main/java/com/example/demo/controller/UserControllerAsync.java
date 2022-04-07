package com.example.demo.controller;

import com.example.demo.annotion.IsDelete;
import com.example.demo.annotion.IsGet;
import com.example.demo.annotion.IsPost;
import com.example.demo.annotion.IsPut;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.request.UserCreateRequest;
import com.example.demo.request.UserUpdateRequest;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import vn.com.itechcorp.base.api.method.AsyncBaseDtoAPIMethod;
import vn.com.itechcorp.base.api.response.APIListResponse;
import vn.com.itechcorp.base.api.response.APIResponse;
import vn.com.itechcorp.base.service.filter.PaginationInfo;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/api/async/user")
public class UserControllerAsync extends AsyncBaseDtoAPIMethod<UserDto, User, Long> {
    private final UserService userService;

    public UserControllerAsync(UserServiceImpl service, UserService userService) {
        super(service);
        this.userService = userService;
    }

    @Override
    @GetMapping("/all")
    @IsGet
    @Async
    public CompletableFuture<ResponseEntity<APIListResponse<List<UserDto>>>> getListAsync(PaginationInfo pageInfo) {
        if (Objects.isNull(pageInfo))
            pageInfo = new PaginationInfo();
        Executors.newSingleThreadExecutor().submit(() -> {
            System.out.println("sdadada");
            // yourCode

            System.out.println("sdawwdada");
        });
        return super.getListAsync(pageInfo);
    }


    @PutMapping("/update/{id}")
    @IsPut
    public CompletableFuture<ResponseEntity<APIResponse<UserDto>>> update(@RequestBody UserUpdateRequest object, @PathVariable Long id) {
        return CompletableFuture.completedFuture(userService.updateCustom(object));
    }

    @IsDelete
    @DeleteMapping("/delete/{id}")
    public CompletableFuture<ResponseEntity<Object>> deleteCustom(@PathVariable Long id) {
        return CompletableFuture.completedFuture(this.delete(id));
    }

    @PostMapping("/add")
    @IsPost
    public CompletableFuture<ResponseEntity<APIResponse<UserDto>>> createAdd(@RequestBody UserCreateRequest object) {
        return CompletableFuture.completedFuture(userService.createCustom(object));
    }

    @GetMapping("/{id}")
    @IsGet
    public CompletableFuture<ResponseEntity<APIResponse<UserDto>>> getByIdCustom(@PathVariable Long id) {
        return CompletableFuture.completedFuture(super.getById(id));
    }

}
