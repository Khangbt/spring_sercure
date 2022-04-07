package com.example.demo.request;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import lombok.Getter;
import lombok.Setter;
import vn.com.itechcorp.base.service.dto.DtoUpdate;

import java.util.List;

@Getter
@Setter
public class UserUpdateRequest extends DtoUpdate<User, Long> {

    private Long id;

    private String fullName;

    private List<Role> roleList;

    @Override
    public boolean apply(User user) {
        return false;
    }
}
