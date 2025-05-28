package com.dra.event_management_system.service;

import java.util.List;
import java.util.UUID;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dra.event_management_system.dto.EventDto;
import com.dra.event_management_system.dto.EventFilterRequest;
import com.dra.event_management_system.dto.UserData;
import com.dra.event_management_system.entity.EventEntity;
import com.dra.event_management_system.entity.UserEntity;
import com.dra.event_management_system.exception.NotFoundException;
import com.dra.event_management_system.repository.EventRepository;
import com.dra.event_management_system.specification.EventSpecification;
import com.dra.event_management_system.util.SecurityUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final ObjectMapper objectMapper;

    // @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @CacheEvict(value = "events", allEntries = true)
    public EventDto createEvent(EventDto eventData){
        EventEntity eventEntity =  this.objectMapper.convertValue(eventData, EventEntity.class);

        UserData authUserData = SecurityUtil.getAuthUser();
        UserEntity authUserEntity = this.objectMapper.convertValue(authUserData, UserEntity.class);
        eventEntity.setHostUser(authUserEntity);

        EventEntity savedEventEntity = this.eventRepository.save(eventEntity);
        EventDto savedEventData =  this.objectMapper.convertValue(savedEventEntity, EventDto.class);
        return savedEventData;
    }

    @Transactional
    @PreAuthorize("@securityService.isHostOrAdminForEvent(#id, principal)")
    @CacheEvict(value = "events", allEntries = true)
    public EventDto updateEvent(UUID id, EventDto eventData){

        EventEntity eventEntity = this.eventRepository.findById(id)
                                            .orElseThrow(() -> new NotFoundException("Event is not found"));

        eventEntity.setTitle(eventData.getTitle());
        eventEntity.setDescription(eventData.getTitle());
        eventEntity.setLocation(eventData.getLocation());
        eventEntity.setStartTime(eventData.getStartTime());
        eventEntity.setEndTime(eventData.getEndTime());
        eventEntity.setVisibility(eventData.getVisibility());

        EventEntity savedEventEntity = this.eventRepository.save(eventEntity);
        EventDto savedEventData =  this.objectMapper.convertValue(savedEventEntity, EventDto.class);
        return savedEventData;
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Cacheable(value = "events", key = "#filter.date + '-' + #filter.location + '-' + #filter.visibility")
    public List<EventDto> filterEvents(EventFilterRequest filter){
        Specification<EventEntity> spec = Specification
                                            .where(EventSpecification.hasLocation(filter.getLocation()))
                                            .and(EventSpecification.hasVisibility(filter.getVisibility()))
                                            .and(EventSpecification.betweenDate(filter.getDate()))
                                            .and(EventSpecification.hasAccessToEvent());

        List<EventEntity> dataList = this.eventRepository.findAll(spec);
        List<EventDto> dtoList = dataList.stream()
                                        .map(x -> this.objectMapper.convertValue(x, EventDto.class))
                                        .toList();
        return dtoList;
    }

}
