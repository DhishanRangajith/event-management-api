package com.dra.event_management_system.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import com.dra.event_management_system.entity.EventEntity;

@Service
public interface EventRepository extends JpaRepository<EventEntity, UUID>, JpaSpecificationExecutor<EventEntity>{
    
    @Query("SELECT e FROM EventEntity e JOIN FETCH e.hostUser WHERE e.id = :eventId")
    Optional<EventEntity> findByIdWithHostUser(@Param("eventId") UUID eventId);

}
