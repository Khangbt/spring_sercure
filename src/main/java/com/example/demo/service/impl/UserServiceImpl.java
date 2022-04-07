package com.example.demo.service.impl;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.UserCreateRequest;
import com.example.demo.request.UserUpdateRequest;
import com.example.demo.service.UserService;
import com.example.demo.unit.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.com.itechcorp.base.api.response.APIResponse;
import vn.com.itechcorp.base.api.response.APIResponseHeader;
import vn.com.itechcorp.base.api.response.APIResponseStatus;
import vn.com.itechcorp.base.persistence.repository.AuditableRepository;
import vn.com.itechcorp.base.service.impl.AuditableDtoJpaServiceImpl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends AuditableDtoJpaServiceImpl<UserDto, User, Long> implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }


    @Override
    public AuditableRepository<User, Long> getRepository() {
        return userRepository;
    }

    @Override
    public UserDto convert(User user) {
        UserDto userDto = new UserDto();
        userDto.parse(user);
        return userDto;
    }


    @Override
    public Optional<User> getByUser(String name) {
        return userRepository.findFirstByName(name);
    }

    @Override
    public ResponseEntity<APIResponse<UserDto>> createCustom(UserCreateRequest userCreateRequest) {
        User userSave = userCreateRequest.toEntry();
        Optional<User> optional = userRepository.findFirstByName(userCreateRequest.getName());
        if (optional.isPresent()) {
            return new ResponseEntity(new APIResponse(new APIResponseHeader(APIResponseStatus.DUPLICATED, Constants.DUPLICATE_RECORD_NAME), (Object) null), HttpStatus.BAD_REQUEST);
        }
        userSave.setPassWork(passwordEncoder.encode(userSave.getPassWork()));
        User user = userRepository.save(userSave);
        UserDto userDto = new UserDto();
        userDto.parse(user);
        return new ResponseEntity(new APIResponse(new APIResponseHeader(APIResponseStatus.CREATED, "New object created with ID " + user.getId()),
                userDto), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<APIResponse<UserDto>> updateCustom(UserUpdateRequest userUpdateRequest) {
        if (Objects.isNull(userUpdateRequest.getId()))
            return new ResponseEntity(new APIResponse(new APIResponseHeader(APIResponseStatus.INVALID_PARAMETER, Constants.NOT_ID), (Object) null), HttpStatus.BAD_REQUEST);

        Optional<User> userOptional = userRepository.findById(userUpdateRequest.getId());
        if (!userOptional.isPresent())
            return new ResponseEntity(new APIResponse(new APIResponseHeader(APIResponseStatus.NOT_FOUND, Constants.NOT_OBJECT), (Object) null), HttpStatus.NOT_FOUND);
        userOptional.get().setFullName(userUpdateRequest.getFullName());
        List<Role> roleList = roleRepository.findAllById(userUpdateRequest.getRoleList().stream().map(Role::getId).collect(Collectors.toSet()));
        userOptional.get().setRoleList(roleList);
        User output = userRepository.save(userOptional.get());
        UserDto userDto = new UserDto();
        userDto.parse(output);

        return new ResponseEntity(new APIResponse(new APIResponseHeader(APIResponseStatus.UPDATED, "New object created with ID " + userDto.getId()),
                userDto), HttpStatus.OK);
    }
}
