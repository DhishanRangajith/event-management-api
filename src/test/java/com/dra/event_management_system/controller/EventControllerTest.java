package com.dra.event_management_system.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.dra.event_management_system.config.filter.JwtAuthFilter;
import com.dra.event_management_system.dto.EventDto;
import com.dra.event_management_system.dto.EventFilterRequest;
import com.dra.event_management_system.dto.UserData;
import com.dra.event_management_system.enums.Role;
import com.dra.event_management_system.service.EventService;
import com.dra.event_management_system.service.UserService;
import com.dra.event_management_system.util.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = EventController.class)
@AutoConfigureMockMvc(addFilters = true)
// @Import(SecurityConfig.class)
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private  EventService eventService; 

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private AuthenticationEntryPoint authenticationEntryPoint;

    // @BeforeAll
    // void init(){
    //     UserData userData = new UserData();
    //     userData.setRole(Role.ADMIN);
    //     userData.setEmail("dra");
        
    // }

    @Test
    @WithMockUser()
    public void testGetEventList() throws Exception {
        List<EventDto> mockEvents = new ArrayList<>();
        when(eventService.filterEvents(any(EventFilterRequest.class))).thenReturn(mockEvents);
        mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @WithMockUser()
    public void testCreateEvent() throws Exception {

        EventDto inputDto = new EventDto();
        inputDto.setTitle("Test Event");

        UUID uuid = UUID.randomUUID();
        EventDto savedDto = new EventDto();
        savedDto.setId(uuid);
        savedDto.setTitle("Test Event");

        // Mocking the service
        when(eventService.createEvent(any(EventDto.class))).thenReturn(savedDto);

        mockMvc.perform(
                    post("/events")
                    .contentType("application/json")
                    .content(new ObjectMapper().writeValueAsString(inputDto))
                )
                .andExpect(status().isCreated())
                .andExpect(content().json("""
                    {
                        "id": 1,
                        "name": "Test Event"
                    }
                """));
    }

    // @Test
    // public void testUpdateEvent() {
        
    // }
}

// @TestConfiguration
// static class NoJwtFilterConfig{
//     @Bean
//     public JwtAuthFilter jwtAuthFilter() {
//         return mock(JwtAuthFilter.class); // or return a no-op filter
//     }
// }
