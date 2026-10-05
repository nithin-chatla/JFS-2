package com.campusconnect.model;

import jakarta.persistence.*;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code; // e.g., "CSE", "ECE", "MECH"

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    private String headOfDepartment;
    private String building;
    private String contactEmail;
    private int studentCount = 0;
    private int facultyCount = 0;

    public Department() {}

    public Department(Long id, String code, String name, String description, String headOfDepartment, String building, String contactEmail, int studentCount, int facultyCount) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.headOfDepartment = headOfDepartment;
        this.building = building;
        this.contactEmail = contactEmail;
        this.studentCount = studentCount;
        this.facultyCount = facultyCount;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getHeadOfDepartment() { return headOfDepartment; }
    public void setHeadOfDepartment(String headOfDepartment) { this.headOfDepartment = headOfDepartment; }

    public String getBuilding() { return building; }
    public void setBuilding(String building) { this.building = building; }

    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }

    public int getStudentCount() { return studentCount; }
    public void setStudentCount(int studentCount) { this.studentCount = studentCount; }

    public int getFacultyCount() { return facultyCount; }
    public void setFacultyCount(int facultyCount) { this.facultyCount = facultyCount; }
}
