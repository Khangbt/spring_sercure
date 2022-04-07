package com.example.demo.request;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import lombok.Getter;
import lombok.Setter;
import vn.com.itechcorp.base.service.dto.BaseDtoCreate;

import java.util.List;

@Getter
@Setter
public class UserCreateRequest extends BaseDtoCreate<User, Long> {
    private String name;
    private String pass;
    private String fullName;
    private Long id;

    private List<Role> roleList;

    @Override
    public User toEntry() {
        return new User(fullName, pass, name, roleList);
    }

    @Override
    public Long getId() {
        return id;
    }
}
