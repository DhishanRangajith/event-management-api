package com.dra.event_management_system.util;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ValidationUtil {

        public static void validateIdsMatch(UUID pathId, UUID dtoId) {
        if (dtoId == null || !dtoId.equals(pathId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "ID in path and request body must be the same");
        }
    }

}
