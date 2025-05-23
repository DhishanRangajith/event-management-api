package com.dra.event_management_system.service;

import java.util.List;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.dra.event_management_system.dto.AuthResponse;
import com.dra.event_management_system.dto.LoginRequest;
import com.dra.event_management_system.entity.UserEntity;
import com.dra.event_management_system.repository.UserRepository;
import com.dra.event_management_system.util.JwtService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService{

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = this.userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Email not found"));

        User user = new User(
            userEntity.getEmail(), 
            userEntity.getPassword(), 
            List.of(new SimpleGrantedAuthority("ROLE_" + userEntity.getRole().name())));

        return user;
    }

    public AuthResponse authentication(LoginRequest loginRequest, AuthenticationManager authenticationManager){
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), 
                loginRequest.getPassword()
            )
        );

        String token = jwtService.generateToken(auth);
        return new AuthResponse(token);
    }

}
