package com.example.demo.dto;

import com.example.demo.entity.User;
import lombok.Getter;
import lombok.Setter;
import vn.com.itechcorp.base.service.dto.DtoGet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
public class UserDto extends DtoGet<User, Long> {
    @Override
    public void parse(User user) {
        this.setId(user.getId());
        this.name = user.getName();
        this.fullName = user.getFullName();
        Collection<RoleDto> roleDtos = new HashSet<>();
        if (Objects.nonNull(user.getRoleList())) {
            user.getRoleList().forEach(role -> {
                RoleDto dto = new RoleDto();
                dto.parse(role);
                roleDtos.add(dto);
            });
        }
        this.roleList = roleDtos;
    }

    private String name;
    private String fullName;
    private Collection<RoleDto> roleList;
}
