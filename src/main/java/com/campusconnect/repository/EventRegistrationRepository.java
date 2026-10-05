package com.campusconnect.repository;

import com.campusconnect.model.EventRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRegistrationRepository extends JpaRepository<EventRegistration, Long> {
    List<EventRegistration> findByEventId(Long eventId);
    List<EventRegistration> findByStudentId(Long studentId);
    Optional<EventRegistration> findByEventIdAndStudentId(Long eventId, Long studentId);
    Optional<EventRegistration> findByTicketCode(String ticketCode);
    boolean existsByEventIdAndStudentId(Long eventId, Long studentId);
    long countByEventId(Long eventId);
    long countByEventIdAndStatus(Long eventId, String status);
}
