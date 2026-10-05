package com.campusconnect.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "clubs")
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String category; // "TECHNICAL", "CULTURAL", "SPORTS", "ARTS", "SOCIAL"

    @Column(length = 2000)
    private String description;

    private String coordinatorName;
    private String coordinatorEmail;
    private String facultyAdvisor;
    private String bannerUrl;
    private String logoIcon; // e.g., "code", "music", "trophy"
    private int memberCount = 0;
    private String status = "ACTIVE";
    private LocalDateTime establishedDate = LocalDateTime.now();

    public Club() {}

    public Club(Long id, String name, String category, String description, String coordinatorName, String coordinatorEmail, String facultyAdvisor, String bannerUrl, String logoIcon, int memberCount) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.coordinatorName = coordinatorName;
        this.coordinatorEmail = coordinatorEmail;
        this.facultyAdvisor = facultyAdvisor;
        this.bannerUrl = bannerUrl;
        this.logoIcon = logoIcon;
        this.memberCount = memberCount;
        this.establishedDate = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCoordinatorName() { return coordinatorName; }
    public void setCoordinatorName(String coordinatorName) { this.coordinatorName = coordinatorName; }

    public String getCoordinatorEmail() { return coordinatorEmail; }
    public void setCoordinatorEmail(String coordinatorEmail) { this.coordinatorEmail = coordinatorEmail; }

    public String getFacultyAdvisor() { return facultyAdvisor; }
    public void setFacultyAdvisor(String facultyAdvisor) { this.facultyAdvisor = facultyAdvisor; }

    public String getBannerUrl() { return bannerUrl; }
    public void setBannerUrl(String bannerUrl) { this.bannerUrl = bannerUrl; }

    public String getLogoIcon() { return logoIcon; }
    public void setLogoIcon(String logoIcon) { this.logoIcon = logoIcon; }

    public int getMemberCount() { return memberCount; }
    public void setMemberCount(int memberCount) { this.memberCount = memberCount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getEstablishedDate() { return establishedDate; }
    public void setEstablishedDate(LocalDateTime establishedDate) { this.establishedDate = establishedDate; }
}
