package com.dra.event_management_system.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.dra.event_management_system.enums.VISIBILITY;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "events")
@Setter
@Getter
public class EventEntity extends CommonEntity{

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @JoinColumn(name = "host_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private UserEntity hostUser;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "location")
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility")
    private VISIBILITY visibility;

}
