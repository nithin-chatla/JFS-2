package com.campusconnect.dto;

import java.util.Map;
import java.util.List;

public class DashboardStatsDTO {
    private long totalStudents;
    private long totalFaculty;
    private long totalEvents;
    private long totalClubs;
    private long totalRegistrations;
    private long activeNotices;
    private long pendingQueries;

    private Map<String, Long> eventsByCategory;
    private Map<String, Long> studentsByDepartment;
    private List<Map<String, Object>> topPopularEvents;
    private List<Map<String, Object>> recentActivities;

    public DashboardStatsDTO() {}

    public long getTotalStudents() { return totalStudents; }
    public void setTotalStudents(long totalStudents) { this.totalStudents = totalStudents; }

    public long getTotalFaculty() { return totalFaculty; }
    public void setTotalFaculty(long totalFaculty) { this.totalFaculty = totalFaculty; }

    public long getTotalEvents() { return totalEvents; }
    public void setTotalEvents(long totalEvents) { this.totalEvents = totalEvents; }

    public long getTotalClubs() { return totalClubs; }
    public void setTotalClubs(long totalClubs) { this.totalClubs = totalClubs; }

    public long getTotalRegistrations() { return totalRegistrations; }
    public void setTotalRegistrations(long totalRegistrations) { this.totalRegistrations = totalRegistrations; }

    public long getActiveNotices() { return activeNotices; }
    public void setActiveNotices(long activeNotices) { this.activeNotices = activeNotices; }

    public long getPendingQueries() { return pendingQueries; }
    public void setPendingQueries(long pendingQueries) { this.pendingQueries = pendingQueries; }

    public Map<String, Long> getEventsByCategory() { return eventsByCategory; }
    public void setEventsByCategory(Map<String, Long> eventsByCategory) { this.eventsByCategory = eventsByCategory; }

    public Map<String, Long> getStudentsByDepartment() { return studentsByDepartment; }
    public void setStudentsByDepartment(Map<String, Long> studentsByDepartment) { this.studentsByDepartment = studentsByDepartment; }

    public List<Map<String, Object>> getTopPopularEvents() { return topPopularEvents; }
    public void setTopPopularEvents(List<Map<String, Object>> topPopularEvents) { this.topPopularEvents = topPopularEvents; }

    public List<Map<String, Object>> getRecentActivities() { return recentActivities; }
    public void setRecentActivities(List<Map<String, Object>> recentActivities) { this.recentActivities = recentActivities; }
}
