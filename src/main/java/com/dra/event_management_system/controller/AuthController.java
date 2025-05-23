package com.dra.event_management_system.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dra.event_management_system.dto.AuthResponse;
import com.dra.event_management_system.dto.LoginRequest;
import com.dra.event_management_system.dto.RegisterRequest;
import com.dra.event_management_system.dto.UserData;
import com.dra.event_management_system.service.AuthService;
import com.dra.event_management_system.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest){
        UserData userData = this.userService.saveUser(registerRequest);
        return new ResponseEntity<>(userData, HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        AuthResponse authResponse = this.authService.authentication(loginRequest, authenticationManager);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }


}
