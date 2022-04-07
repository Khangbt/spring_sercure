package com.example.demo.dto;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import lombok.Data;
import vn.com.itechcorp.base.service.dto.DtoGet;

@Data
public class RoleDto extends DtoGet<Role, Long> {
    @Override
    public void parse(Role role) {
        this.name = role.getName();
        this.setId(role.getId());
    }

    private String name;
}
