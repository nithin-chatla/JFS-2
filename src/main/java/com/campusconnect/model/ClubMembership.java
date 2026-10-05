package com.campusconnect.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "club_memberships")
public class ClubMembership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clubId;
    private String clubName;
    private Long studentId;
    private String studentName;
    private String studentEmail;
    private String department;
    private String roleInClub = "MEMBER"; // "MEMBER", "CORE_TEAM", "LEAD"
    private String status = "APPROVED"; // "PENDING", "APPROVED", "REJECTED"
    private LocalDateTime joinedAt = LocalDateTime.now();

    public ClubMembership() {}

    public ClubMembership(Long clubId, String clubName, Long studentId, String studentName, String studentEmail, String department, String roleInClub, String status) {
        this.clubId = clubId;
        this.clubName = clubName;
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.department = department;
        this.roleInClub = roleInClub;
        this.status = status;
        this.joinedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getClubId() { return clubId; }
    public void setClubId(Long clubId) { this.clubId = clubId; }

    public String getClubName() { return clubName; }
    public void setClubName(String clubName) { this.clubName = clubName; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getStudentEmail() { return studentEmail; }
    public void setStudentEmail(String studentEmail) { this.studentEmail = studentEmail; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getRoleInClub() { return roleInClub; }
    public void setRoleInClub(String roleInClub) { this.roleInClub = roleInClub; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getJoinedAt() { return joinedAt; }
    public void setJoinedAt(LocalDateTime joinedAt) { this.joinedAt = joinedAt; }
}
