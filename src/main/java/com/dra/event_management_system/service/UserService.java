package com.dra.event_management_system.service;

import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dra.event_management_system.dto.RegisterRequest;
import com.dra.event_management_system.dto.UserData;
import com.dra.event_management_system.entity.UserEntity;
import com.dra.event_management_system.enums.Role;
import com.dra.event_management_system.exception.NotFoundException;
import com.dra.event_management_system.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService{

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserData getUserByEmail(String email){
        Optional<UserEntity> userEntity = this.userRepository.findByEmail(email);
        UserData userDto = objectMapper.convertValue(
                userEntity.orElseThrow(() -> new NotFoundException("User not found")), 
                UserData.class
            );
        return userDto;
    }

    public boolean emailExists(String email){
        return this.userRepository.existsByEmail(email);
    }

    public UserData saveUser(RegisterRequest registerRequest){
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(registerRequest.getEmail());
        userEntity.setName(registerRequest.getName());
        userEntity.setRole(Role.USER);
        userEntity.setPassword(this.passwordEncoder.encode(registerRequest.getPassword()));
        UserEntity savedUserEntity = this.userRepository.save(userEntity);
        UserData userDto = objectMapper.convertValue(savedUserEntity, UserData.class);
        return userDto;
    }


}
