package com.blooddonation.blood.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "coordination")
public class Coordination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "recipient", nullable = false)
    private String recipient;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String information;
    
    @Column(name = "status")
    private String status = "Pending";
    
    @Column(name = "priority")
    private String priority = "Normal";
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    // Constructors
    public Coordination() {
        this.createdAt = LocalDateTime.now();
    }
    
    public Coordination(String recipient, String information) {
        this();
        this.recipient = recipient;
        this.information = information;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getRecipient() {
        return recipient;
    }
    
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
    
    public String getInformation() {
        return information;
    }
    
    public void setInformation(String information) {
        this.information = information;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getPriority() {
        return priority;
    }
    
    public void setPriority(String priority) {
        this.priority = priority;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

