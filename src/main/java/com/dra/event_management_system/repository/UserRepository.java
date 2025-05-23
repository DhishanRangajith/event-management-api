package com.dra.event_management_system.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dra.event_management_system.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID>{

    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);

}
