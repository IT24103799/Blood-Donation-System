package com.bloodDonation.BloodDonationSystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "blood_requests")
public class BloodRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "blood_type", nullable = false)
    private BloodType bloodType;
    
    @Min(value = 1, message = "Amount must be at least 1 ml")
    @Column(nullable = false)
    private Integer amount;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "emergency_level", nullable = false)
    private EmergencyLevel emergencyLevel;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requested_by_staff_id")
    private Staff requestedBy;
    
    @Column(name = "request_date")
    private LocalDateTime requestDate;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RequestStatus status = RequestStatus.PENDING;
    
    @Column(name = "notes", length = 500)
    private String notes;
    
    // Constructors
    public BloodRequest() {
        this.requestDate = LocalDateTime.now();
    }
    
    public BloodRequest(BloodType bloodType, Integer amount, EmergencyLevel emergencyLevel, Staff requestedBy) {
        this();
        this.bloodType = bloodType;
        this.amount = amount;
        this.emergencyLevel = emergencyLevel;
        this.requestedBy = requestedBy;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public BloodType getBloodType() {
        return bloodType;
    }
    
    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }
    
    public Integer getAmount() {
        return amount;
    }
    
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    
    public EmergencyLevel getEmergencyLevel() {
        return emergencyLevel;
    }
    
    public void setEmergencyLevel(EmergencyLevel emergencyLevel) {
        this.emergencyLevel = emergencyLevel;
    }
    
    public Staff getRequestedBy() {
        return requestedBy;
    }
    
    public void setRequestedBy(Staff requestedBy) {
        this.requestedBy = requestedBy;
    }
    
    public LocalDateTime getRequestDate() {
        return requestDate;
    }
    
    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }
    
    public RequestStatus getStatus() {
        return status;
    }
    
    public void setStatus(RequestStatus status) {
        this.status = status;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    @Override
    public String toString() {
        return "BloodRequest{" +
                "id=" + id +
                ", bloodType=" + bloodType +
                ", amount=" + amount +
                ", emergencyLevel=" + emergencyLevel +
                ", status=" + status +
                ", requestDate=" + requestDate +
                '}';
    }
}
