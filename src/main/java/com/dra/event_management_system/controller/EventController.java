package com.dra.event_management_system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dra.event_management_system.dto.EventDto;
import com.dra.event_management_system.dto.EventFilterRequest;
import com.dra.event_management_system.service.EventService;
import com.dra.event_management_system.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<List<EventDto>> getEventList(
        @RequestParam(required = false) String location, 
        @RequestParam(required = false) String visibility, 
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        EventFilterRequest filter = new EventFilterRequest(date, location, visibility);
        List<EventDto> result = eventService.filterEvents(filter);
        return new ResponseEntity<List<EventDto>>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody EventDto eventDto) {
        EventDto savedData = this.eventService.createEvent(eventDto);
        return new ResponseEntity<>(savedData, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateEvent(@PathVariable UUID id, @RequestBody EventDto eventDto) {
        ValidationUtil.validateIdsMatch(id, eventDto.getId());
        EventDto savedData = this.eventService.updateEvent(id, eventDto);
        return new ResponseEntity<>(savedData, HttpStatus.OK);
    }

    
    

}
