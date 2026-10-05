package com.campusconnect.service;

import com.campusconnect.dto.DashboardStatsDTO;
import com.campusconnect.model.Event;
import com.campusconnect.model.EventRegistration;
import com.campusconnect.model.User;
import com.campusconnect.repository.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final EventRegistrationRepository registrationRepository;
    private final ClubRepository clubRepository;
    private final NoticeRepository noticeRepository;
    private final FeedbackQueryRepository feedbackRepository;

    public AnalyticsService(UserRepository userRepository, EventRepository eventRepository,
                            EventRegistrationRepository registrationRepository, ClubRepository clubRepository,
                            NoticeRepository noticeRepository, FeedbackQueryRepository feedbackRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.registrationRepository = registrationRepository;
        this.clubRepository = clubRepository;
        this.noticeRepository = noticeRepository;
        this.feedbackRepository = feedbackRepository;
    }

    public DashboardStatsDTO getDashboardStats() {
        DashboardStatsDTO stats = new DashboardStatsDTO();

        long studentCount = userRepository.findByRole("STUDENT").size();
        long facultyCount = userRepository.findByRole("FACULTY").size();
        long eventCount = eventRepository.count();
        long clubCount = clubRepository.count();
        long regCount = registrationRepository.count();
        long noticeCount = noticeRepository.count();
        long pendingQueries = feedbackRepository.findByStatus("PENDING").size();

        stats.setTotalStudents(studentCount);
        stats.setTotalFaculty(facultyCount);
        stats.setTotalEvents(eventCount);
        stats.setTotalClubs(clubCount);
        stats.setTotalRegistrations(regCount);
        stats.setActiveNotices(noticeCount);
        stats.setPendingQueries(pendingQueries);

        // Events by category
        Map<String, Long> eventsByCategory = eventRepository.findAll().stream()
                .collect(Collectors.groupingBy(Event::getCategory, Collectors.counting()));
        stats.setEventsByCategory(eventsByCategory);

        // Students by department
        Map<String, Long> studentsByDept = userRepository.findByRole("STUDENT").stream()
                .filter(u -> u.getDepartment() != null && !u.getDepartment().isEmpty())
                .collect(Collectors.groupingBy(User::getDepartment, Collectors.counting()));
        stats.setStudentsByDepartment(studentsByDept);

        // Top popular events
        List<Map<String, Object>> topEvents = eventRepository.findAll().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getRegisteredCount(), e1.getRegisteredCount()))
                .limit(5)
                .map(e -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", e.getId());
                    map.put("title", e.getTitle());
                    map.put("category", e.getCategory());
                    map.put("capacity", e.getCapacity());
                    map.put("registeredCount", e.getRegisteredCount());
                    map.put("fillRate", e.getCapacity() > 0 ? (int) ((double) e.getRegisteredCount() / e.getCapacity() * 100) : 0);
                    return map;
                })
                .collect(Collectors.toList());
        stats.setTopPopularEvents(topEvents);

        // Recent activities
        List<Map<String, Object>> recentActivities = new ArrayList<>();
        List<EventRegistration> recentRegs = registrationRepository.findAll().stream()
                .sorted((r1, r2) -> r2.getRegisteredAt().compareTo(r1.getRegisteredAt()))
                .limit(6)
                .collect(Collectors.toList());

        for (EventRegistration r : recentRegs) {
            Map<String, Object> act = new HashMap<>();
            act.put("type", "REGISTRATION");
            act.put("title", r.getStudentName() + " registered for " + r.getEventTitle());
            act.put("time", r.getRegisteredAt().toString());
            act.put("ticketCode", r.getTicketCode());
            recentActivities.add(act);
        }
        stats.setRecentActivities(recentActivities);

        return stats;
    }

    public String generateRegistrationsCsv() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ticket Code,Event ID,Event Title,Student ID,Student Name,Student Email,Department,Roll Number,Phone,Status,Amount Paid,Registered At\n");

        List<EventRegistration> list = registrationRepository.findAll();
        for (EventRegistration r : list) {
            sb.append(escapeCsv(r.getTicketCode())).append(",")
              .append(r.getEventId()).append(",")
              .append(escapeCsv(r.getEventTitle())).append(",")
              .append(r.getStudentId()).append(",")
              .append(escapeCsv(r.getStudentName())).append(",")
              .append(escapeCsv(r.getStudentEmail())).append(",")
              .append(escapeCsv(r.getDepartment())).append(",")
              .append(escapeCsv(r.getRollNumber())).append(",")
              .append(escapeCsv(r.getPhone())).append(",")
              .append(escapeCsv(r.getStatus())).append(",")
              .append(r.getAmountPaid()).append(",")
              .append(r.getRegisteredAt()).append("\n");
        }
        return sb.toString();
    }

    public String generateEventsCsv() {
        StringBuilder sb = new StringBuilder();
        sb.append("Event ID,Title,Category,Type,Venue,Organizer,Capacity,Registered Count,Fee,Status,Start Date,End Date\n");

        List<Event> list = eventRepository.findAll();
        for (Event e : list) {
            sb.append(e.getId()).append(",")
              .append(escapeCsv(e.getTitle())).append(",")
              .append(escapeCsv(e.getCategory())).append(",")
              .append(escapeCsv(e.getEventType())).append(",")
              .append(escapeCsv(e.getVenue())).append(",")
              .append(escapeCsv(e.getOrganizerName())).append(",")
              .append(e.getCapacity()).append(",")
              .append(e.getRegisteredCount()).append(",")
              .append(e.getFee()).append(",")
              .append(escapeCsv(e.getStatus())).append(",")
              .append(e.getStartDateTime()).append(",")
              .append(e.getEndDateTime()).append("\n");
        }
        return sb.toString();
    }

    private String escapeCsv(String val) {
        if (val == null) return "";
        if (val.contains(",") || val.contains("\"") || val.contains("\n")) {
            return "\"" + val.replace("\"", "\"\"") + "\"";
        }
        return val;
    }
}
