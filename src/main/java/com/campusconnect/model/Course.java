package com.campusconnect.model;

import jakarta.persistence.*;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code; // e.g., "CS301"

    @Column(nullable = false)
    private String title;

    private String department;
    private int credits = 3;
    private int semester = 1;
    private String instructorName;
    private String description;
    private String syllabusUrl;

    public Course() {}

    public Course(Long id, String code, String title, String department, int credits, int semester, String instructorName, String description) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.department = department;
        this.credits = credits;
        this.semester = semester;
        this.instructorName = instructorName;
        this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }

    public int getSemester() { return semester; }
    public void setSemester(int semester) { this.semester = semester; }

    public String getInstructorName() { return instructorName; }
    public void setInstructorName(String instructorName) { this.instructorName = instructorName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getSyllabusUrl() { return syllabusUrl; }
    public void setSyllabusUrl(String syllabusUrl) { this.syllabusUrl = syllabusUrl; }
}
