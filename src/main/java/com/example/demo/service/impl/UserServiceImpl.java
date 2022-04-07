package com.example.demo.service.impl;

import com.example.demo.dto.RoleDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.com.itechcorp.base.exception.APIException;
import vn.com.itechcorp.base.persistence.repository.AuditableRepository;
import vn.com.itechcorp.base.service.dto.BaseDtoCreate;
import vn.com.itechcorp.base.service.impl.AuditableDtoJpaServiceImpl;

import java.util.*;

@Service
public class UserServiceImpl extends AuditableDtoJpaServiceImpl<UserDto, User, Long> implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public AuditableRepository<User, Long> getRepository() {
        return userRepository;
    }

    @Override
    public UserDto convert(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        Set<RoleDto> roleDtos = new HashSet<>();
        if (Objects.nonNull(user.getRoleList())) {
            user.getRoleList().forEach(role -> {
                RoleDto dto = new RoleDto();
                dto.setId(role.getId());
                dto.setName(role.getName());
                roleDtos.add(dto);
            });
        }
        userDto.setRoleList(roleDtos);
        return userDto;
    }

    @Override
    public UserDto create(BaseDtoCreate<User, Long> entity, String callerId) throws APIException {
        String pass = entity.toEntry().getPassWork();
        entity.toEntry().setPassWork(passwordEncoder.encode(pass));
        return super.create(entity, callerId);
    }

    @Override
    public Optional<User> getByUser(String name) {
        return userRepository.findFirstByName(name);
    }
}
