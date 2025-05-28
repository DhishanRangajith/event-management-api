package com.dra.event_management_system.dto;

import java.util.UUID;

import com.dra.event_management_system.enums.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserData {
    private UUID id;
    private String name;
    private String email;
    private Role role;
}
