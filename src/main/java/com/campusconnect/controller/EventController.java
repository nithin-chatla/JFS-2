package com.campusconnect.controller;

import com.campusconnect.dto.EventRegistrationRequest;
import com.campusconnect.model.Event;
import com.campusconnect.model.EventRegistration;
import com.campusconnect.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(eventService.getAllEvents(category, status, search));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        return eventService.getEventById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        return ResponseEntity.ok(eventService.createEvent(event));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event event) {
        return ResponseEntity.ok(eventService.updateEvent(id, event));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/register")
    public ResponseEntity<?> registerForEvent(@PathVariable Long id, @RequestBody EventRegistrationRequest request) {
        try {
            EventRegistration reg = eventService.registerForEvent(id, request);
            return ResponseEntity.ok(reg);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/registrations")
    public ResponseEntity<List<EventRegistration>> getEventRegistrations(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getRegistrationsByEvent(id));
    }

    @GetMapping("/student/{studentId}/registrations")
    public ResponseEntity<List<EventRegistration>> getStudentRegistrations(@PathVariable Long studentId) {
        return ResponseEntity.ok(eventService.getRegistrationsByStudent(studentId));
    }

    @GetMapping("/registrations/all")
    public ResponseEntity<List<EventRegistration>> getAllRegistrations() {
        return ResponseEntity.ok(eventService.getAllRegistrations());
    }

    @GetMapping("/tickets/{ticketCode}")
    public ResponseEntity<EventRegistration> getByTicketCode(@PathVariable String ticketCode) {
        return eventService.getRegistrationByTicket(ticketCode)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/registrations/{regId}/mark-attendance")
    public ResponseEntity<EventRegistration> markAttendance(@PathVariable Long regId) {
        return ResponseEntity.ok(eventService.markAttendance(regId));
    }

    @PostMapping("/registrations/{regId}/cancel")
    public ResponseEntity<Void> cancelRegistration(@PathVariable Long regId) {
        eventService.cancelRegistration(regId);
        return ResponseEntity.ok().build();
    }
}
