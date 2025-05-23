package com.dra.event_management_system.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.dra.event_management_system.dto.UserData;

public class SecurityUtil {

    public static UserData getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserData) {
            return (UserData) authentication.getPrincipal();
        }
        throw new RuntimeException("No authenticated user found");
    }

}
