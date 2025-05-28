package com.dra.event_management_system.service;

import java.util.UUID;
import org.springframework.stereotype.Component;
import com.dra.event_management_system.dto.UserData;
import com.dra.event_management_system.entity.EventEntity;
import com.dra.event_management_system.enums.Role;
import com.dra.event_management_system.exception.NotFoundException;
import com.dra.event_management_system.repository.EventRepository;
import lombok.RequiredArgsConstructor;

@Component("securityService")
@RequiredArgsConstructor
public class SecurityService {

    private final EventRepository eventRepository;

    public boolean isHostOrAdminForEvent(UUID eventId, UserData userData) {
        if(userData.getRole() == Role.ADMIN) return true;
        
        EventEntity event = this.eventRepository.findByIdWithHostUser(eventId)
                                .orElseThrow(() -> new NotFoundException("Event is not found"));

        return event.getHostUser().getId().equals(userData.getId());
    }





}
