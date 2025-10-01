package com.bloodDonation.BloodDonationSystem.entity;

public enum EmergencyLevel {
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High");
    
    private final String displayName;
    
    EmergencyLevel(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
    
    public static EmergencyLevel fromDisplayName(String displayName) {
        for (EmergencyLevel level : values()) {
            if (level.displayName.equals(displayName)) {
                return level;
            }
        }
        throw new IllegalArgumentException("Unknown emergency level: " + displayName);
    }
}
