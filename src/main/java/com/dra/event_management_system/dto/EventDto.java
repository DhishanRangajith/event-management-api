package com.dra.event_management_system.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
import com.dra.event_management_system.entity.UserEntity;
import com.dra.event_management_system.enums.VISIBILITY;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventDto implements Serializable{
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String title;
    private String description;
    private UserEntity hostUser;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
    private VISIBILITY visibility;

}
