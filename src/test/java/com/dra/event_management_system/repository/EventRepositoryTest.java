package com.dra.event_management_system.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.dra.event_management_system.entity.EventEntity;
import com.dra.event_management_system.entity.UserEntity;
import com.dra.event_management_system.enums.Role;

@DataJpaTest
public class EventRepositoryTest {

    @Autowired 
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    private UserEntity savedUserEntity;

    @BeforeEach
    public void init(){
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("test@gmail.com");
        userEntity.setName("Test User");
        userEntity.setRole(Role.USER);
        this.savedUserEntity = this.userRepository.save(userEntity);
    }

    @Test
    public void testFindByIdWithHostUser(){

        EventEntity eventEntity = new EventEntity();
        eventEntity.setLocation("Sample Location");
        eventEntity.setTitle("Sample Title");
        eventEntity.setHostUser(this.savedUserEntity);
        EventEntity savedEventEntity = this.eventRepository.save(eventEntity);

        Optional<EventEntity> data = this.eventRepository.findByIdWithHostUser(savedEventEntity.getId());

        assertTrue(data.isPresent());
        assertNotNull(data.get().getHostUser());
        assertEquals(this.savedUserEntity.getId(), data.get().getHostUser().getId());
    }

}
