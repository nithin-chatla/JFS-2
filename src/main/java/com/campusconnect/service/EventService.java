package com.campusconnect.service;

import com.campusconnect.dto.EventRegistrationRequest;
import com.campusconnect.model.Event;
import com.campusconnect.model.EventRegistration;
import com.campusconnect.repository.EventRegistrationRepository;
import com.campusconnect.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final EventRegistrationRepository registrationRepository;

    public EventService(EventRepository eventRepository, EventRegistrationRepository registrationRepository) {
        this.eventRepository = eventRepository;
        this.registrationRepository = registrationRepository;
    }

    public List<Event> getAllEvents(String category, String status, String search) {
        if (search != null && !search.trim().isEmpty()) {
            return eventRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(search.trim(), search.trim());
        }
        if (category != null && !category.trim().equalsIgnoreCase("ALL")) {
            return eventRepository.findByCategory(category.trim().toUpperCase());
        }
        if (status != null && !status.trim().equalsIgnoreCase("ALL")) {
            return eventRepository.findByStatus(status.trim().toUpperCase());
        }
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public Event createEvent(Event event) {
        if (event.getStartDateTime() == null) {
            event.setStartDateTime(LocalDateTime.now().plusDays(7));
        }
        if (event.getEndDateTime() == null) {
            event.setEndDateTime(event.getStartDateTime().plusHours(4));
        }
        if (event.getRegistrationDeadline() == null) {
            event.setRegistrationDeadline(event.getStartDateTime().minusDays(1));
        }
        if (event.getStatus() == null) {
            event.setStatus("UPCOMING");
        }
        event.setCreatedAt(LocalDateTime.now());
        return eventRepository.save(event);
    }

    public Event updateEvent(Long id, Event updated) {
        return eventRepository.findById(id).map(event -> {
            event.setTitle(updated.getTitle());
            event.setDescription(updated.getDescription());
            event.setCategory(updated.getCategory());
            event.setEventType(updated.getEventType());
            event.setVenue(updated.getVenue());
            event.setStartDateTime(updated.getStartDateTime());
            event.setEndDateTime(updated.getEndDateTime());
            event.setRegistrationDeadline(updated.getRegistrationDeadline());
            event.setCapacity(updated.getCapacity());
            event.setFee(updated.getFee());
            event.setBannerUrl(updated.getBannerUrl());
            event.setOrganizerName(updated.getOrganizerName());
            event.setOrganizerEmail(updated.getOrganizerEmail());
            event.setOrganizingDepartmentOrClub(updated.getOrganizingDepartmentOrClub());
            event.setStatus(updated.getStatus());
            event.setCertificateProvided(updated.isCertificateProvided());
            return eventRepository.save(event);
        }).orElseThrow(() -> new RuntimeException("Event not found with id " + id));
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    @Transactional
    public EventRegistration registerForEvent(Long eventId, EventRegistrationRequest request) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found with id " + eventId));

        // Check if student already registered
        if (registrationRepository.existsByEventIdAndStudentId(eventId, request.getStudentId())) {
            throw new RuntimeException("Student is already registered for this event!");
        }

        // Check capacity
        if (event.getRegisteredCount() >= event.getCapacity()) {
            throw new RuntimeException("Event capacity reached! Registrations are full.");
        }

        // Generate unique ticket code
        String ticketCode = "CC-" + event.getId() + "-" + (1000 + new Random().nextInt(9000)) + "-" + request.getStudentId();

        EventRegistration reg = new EventRegistration();
        reg.setEventId(event.getId());
        reg.setEventTitle(event.getTitle());
        reg.setEventCategory(event.getCategory());
        reg.setEventStartDateTime(event.getStartDateTime());
        reg.setEventVenue(event.getVenue());
        reg.setStudentId(request.getStudentId());
        reg.setStudentName(request.getStudentName());
        reg.setStudentEmail(request.getStudentEmail());
        reg.setDepartment(request.getDepartment());
        reg.setRollNumber(request.getRollNumber());
        reg.setPhone(request.getPhone());
        reg.setTicketCode(ticketCode);
        reg.setStatus("CONFIRMED");
        reg.setAmountPaid(event.getFee());
        reg.setPaymentStatus(event.getFee() > 0 ? "PAID" : "FREE");
        reg.setRegisteredAt(LocalDateTime.now());

        // Update event registration count
        event.setRegisteredCount(event.getRegisteredCount() + 1);
        eventRepository.save(event);

        return registrationRepository.save(reg);
    }

    public List<EventRegistration> getRegistrationsByEvent(Long eventId) {
        return registrationRepository.findByEventId(eventId);
    }

    public List<EventRegistration> getRegistrationsByStudent(Long studentId) {
        return registrationRepository.findByStudentId(studentId);
    }

    public List<EventRegistration> getAllRegistrations() {
        return registrationRepository.findAll();
    }

    public Optional<EventRegistration> getRegistrationByTicket(String ticketCode) {
        return registrationRepository.findByTicketCode(ticketCode);
    }

    @Transactional
    public EventRegistration markAttendance(Long registrationId) {
        EventRegistration reg = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Registration not found with id " + registrationId));
        reg.setStatus("ATTENDED");
        reg.setCheckInTime(LocalDateTime.now());
        return registrationRepository.save(reg);
    }

    @Transactional
    public void cancelRegistration(Long registrationId) {
        EventRegistration reg = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Registration not found with id " + registrationId));

        // Decrease count
        eventRepository.findById(reg.getEventId()).ifPresent(event -> {
            if (event.getRegisteredCount() > 0) {
                event.setRegisteredCount(event.getRegisteredCount() - 1);
                eventRepository.save(event);
            }
        });

        reg.setStatus("CANCELLED");
        registrationRepository.save(reg);
    }
}
