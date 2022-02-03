package com.quattro.javacrud.repository;

import com.quattro.javacrud.models.ERole;
import com.quattro.javacrud.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
