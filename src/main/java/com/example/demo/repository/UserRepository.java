package com.example.demo.repository;

import com.example.demo.entity.User;
import vn.com.itechcorp.base.persistence.repository.AuditableRepository;

import java.util.Optional;

public interface UserRepository extends AuditableRepository<User, Long> {
    Optional<User> findFirstByName(String name);
}
