package com.example.demo.dto;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import vn.com.itechcorp.base.service.dto.DtoGet;

import javax.persistence.EntityListeners;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class UserDto extends DtoGet<User, Long> {
    @Override
    public void parse(User user) {
    }

    private String name;

    private Collection<RoleDto> roleList;
}
