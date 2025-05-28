package com.dra.event_management_system.init;

import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.dra.event_management_system.entity.UserEntity;
import com.dra.event_management_system.enums.Role;
import com.dra.event_management_system.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initUsers(){
        List<InitUser> initUsers = List.of(
            new InitUser("admin", "admin", "123", Role.ADMIN),
            new InitUser("dhishan", "dhishan@gmail.com", "123456", Role.USER)
        );

        for(InitUser user : initUsers){
            if(!this.userRepository.existsByEmail(user.getEmail())){
                UserEntity userEntity = new UserEntity();
                userEntity.setEmail(user.getEmail());
                userEntity.setName(user.getName());
                userEntity.setRole(user.getRole());
                userEntity.setPassword(this.passwordEncoder.encode(user.getPassword()));
                this.userRepository.save(userEntity);
            }
        }
    }
}

@AllArgsConstructor
@Getter
class InitUser{
    private String name;
    private String email;
    private String password;
    private Role role;
}
