package com.campusconnect.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 3000)
    private String description;

    @Column(nullable = false)
    private String category; // "FEST", "WORKSHOP", "SEMINAR", "SPORTS", "CULTURAL", "HACKATHON", "WEBINAR"

    private String eventType = "OFFLINE"; // "OFFLINE", "ONLINE", "HYBRID"
    private String venue; // e.g. "Main Auditorium" or "Zoom Link"
    
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private LocalDateTime registrationDeadline;

    private int capacity = 100;
    private int registeredCount = 0;
    private double fee = 0.0; // 0.0 for free

    private String bannerUrl;
    private String organizerName;
    private String organizerEmail;
    private String organizingDepartmentOrClub;
    private String status = "UPCOMING"; // "UPCOMING", "ONGOING", "COMPLETED", "CANCELLED"
    private boolean certificateProvided = true;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Event() {}

    public Event(Long id, String title, String description, String category, String eventType, String venue,
                 LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDateTime registrationDeadline,
                 int capacity, int registeredCount, double fee, String bannerUrl, String organizerName,
                 String organizerEmail, String organizingDepartmentOrClub, String status, boolean certificateProvided) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.eventType = eventType;
        this.venue = venue;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.registrationDeadline = registrationDeadline;
        this.capacity = capacity;
        this.registeredCount = registeredCount;
        this.fee = fee;
        this.bannerUrl = bannerUrl;
        this.organizerName = organizerName;
        this.organizerEmail = organizerEmail;
        this.organizingDepartmentOrClub = organizingDepartmentOrClub;
        this.status = status;
        this.certificateProvided = certificateProvided;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }

    public LocalDateTime getStartDateTime() { return startDateTime; }
    public void setStartDateTime(LocalDateTime startDateTime) { this.startDateTime = startDateTime; }

    public LocalDateTime getEndDateTime() { return endDateTime; }
    public void setEndDateTime(LocalDateTime endDateTime) { this.endDateTime = endDateTime; }

    public LocalDateTime getRegistrationDeadline() { return registrationDeadline; }
    public void setRegistrationDeadline(LocalDateTime registrationDeadline) { this.registrationDeadline = registrationDeadline; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public int getRegisteredCount() { return registeredCount; }
    public void setRegisteredCount(int registeredCount) { this.registeredCount = registeredCount; }

    public double getFee() { return fee; }
    public void setFee(double fee) { this.fee = fee; }

    public String getBannerUrl() { return bannerUrl; }
    public void setBannerUrl(String bannerUrl) { this.bannerUrl = bannerUrl; }

    public String getOrganizerName() { return organizerName; }
    public void setOrganizerName(String organizerName) { this.organizerName = organizerName; }

    public String getOrganizerEmail() { return organizerEmail; }
    public void setOrganizerEmail(String organizerEmail) { this.organizerEmail = organizerEmail; }

    public String getOrganizingDepartmentOrClub() { return organizingDepartmentOrClub; }
    public void setOrganizingDepartmentOrClub(String organizingDepartmentOrClub) { this.organizingDepartmentOrClub = organizingDepartmentOrClub; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public boolean isCertificateProvided() { return certificateProvided; }
    public void setCertificateProvided(boolean certificateProvided) { this.certificateProvided = certificateProvided; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
