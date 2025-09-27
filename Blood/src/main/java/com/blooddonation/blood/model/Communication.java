package com.blooddonation.blood.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "communications")
public class Communication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "donor_name", nullable = false)
    private String donorName;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;
    
    @Column(name = "communication_type")
    private String communicationType = "General";
    
    @Column(name = "status")
    private String status = "Sent";
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    // Constructors
    public Communication() {
        this.createdAt = LocalDateTime.now();
    }
    
    public Communication(String donorName, String message) {
        this();
        this.donorName = donorName;
        this.message = message;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getDonorName() {
        return donorName;
    }
    
    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getCommunicationType() {
        return communicationType;
    }
    
    public void setCommunicationType(String communicationType) {
        this.communicationType = communicationType;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

