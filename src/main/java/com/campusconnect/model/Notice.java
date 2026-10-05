package com.campusconnect.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notices")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 4000, nullable = false)
    private String content;

    private String category = "GENERAL"; // "ACADEMIC", "EVENT", "EXAM", "GENERAL", "PLACEMENT", "URGENT"
    private String priority = "MEDIUM"; // "HIGH", "MEDIUM", "LOW"
    private String publishedBy;
    private String targetAudience = "ALL"; // "ALL", "STUDENTS", "FACULTY"
    private boolean isPinned = false;
    private LocalDateTime publishedDate = LocalDateTime.now();

    public Notice() {}

    public Notice(Long id, String title, String content, String category, String priority, String publishedBy, String targetAudience, boolean isPinned) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.priority = priority;
        this.publishedBy = publishedBy;
        this.targetAudience = targetAudience;
        this.isPinned = isPinned;
        this.publishedDate = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public String getPublishedBy() { return publishedBy; }
    public void setPublishedBy(String publishedBy) { this.publishedBy = publishedBy; }

    public String getTargetAudience() { return targetAudience; }
    public void setTargetAudience(String targetAudience) { this.targetAudience = targetAudience; }

    public boolean isPinned() { return isPinned; }
    public void setPinned(boolean pinned) { isPinned = pinned; }

    public LocalDateTime getPublishedDate() { return publishedDate; }
    public void setPublishedDate(LocalDateTime publishedDate) { this.publishedDate = publishedDate; }
}
