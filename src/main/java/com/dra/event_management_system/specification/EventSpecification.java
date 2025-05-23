package com.dra.event_management_system.specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.data.jpa.domain.Specification;

import com.dra.event_management_system.dto.UserData;
import com.dra.event_management_system.entity.EventEntity;
import com.dra.event_management_system.enums.VISIBILITY;
import com.dra.event_management_system.util.SecurityUtil;

import jakarta.persistence.criteria.Predicate;

public class EventSpecification {

    public static Specification<EventEntity> hasLocation(String location) {
        return (root, query, cb) -> {
            if (location == null || location.isBlank()) return null;
            return cb.like(cb.lower(root.get("location")), "%" + location.toLowerCase() + "%");
        };
    }

    public static Specification<EventEntity> hasVisibility(String visibility) {
        return (root, query, cb) -> {
            if (visibility == null) return null;
            return cb.equal(root.get("visibility"), visibility);
        };
    }

    public static Specification<EventEntity> betweenDate(LocalDate date) {
        return (root, query, cb) -> {
            if (date == null) return null;
            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = date.plusDays(1).atStartOfDay().minusNanos(1);
            return cb.and(
                cb.lessThanOrEqualTo(root.get("startTime"), endOfDay),
                cb.greaterThanOrEqualTo(root.get("endTime"), startOfDay)
            );
        };
    }

    public static Specification<EventEntity> hasAccessToEvent() {
        return (root, query, cb) -> {
            UserData authUser = SecurityUtil.getAuthUser();
            Predicate isPublic = cb.equal(root.get("visibility"), VISIBILITY.PUBLIC.name());
            Predicate isOwner = cb.equal(root.get("hostUser").get("id"), authUser.getId());
            return cb.or(isPublic, isOwner);
        };
    }

}

