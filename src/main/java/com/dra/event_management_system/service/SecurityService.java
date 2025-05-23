package com.dra.event_management_system.service;

import java.security.Principal;
import java.util.UUID;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.dra.event_management_system.dto.UserData;
import com.dra.event_management_system.entity.EventEntity;
import com.dra.event_management_system.enums.ROLE;
import com.dra.event_management_system.exception.NotFoundException;
import com.dra.event_management_system.repository.EventRepository;
import lombok.RequiredArgsConstructor;

@Component("securityService")
@RequiredArgsConstructor
public class SecurityService {

    private final EventRepository eventRepository;

    public boolean isHostOrAdminForEvent(UUID eventId, UserData userData) {
        if(userData.getRole() == ROLE.ADMIN) return true;
        
        EventEntity event = this.eventRepository.findByIdWithHostUser(eventId)
                                .orElseThrow(() -> new NotFoundException("Event is not found"));

        return event.getHostUser().getId().equals(userData.getId());
    }





}
