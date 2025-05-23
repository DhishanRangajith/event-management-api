package com.dra.event_management_system.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class EventFilterRequest {
    private LocalDate date;
    private String location;
    private String visibility;
}
