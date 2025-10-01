package com.bloodDonation.BloodDonationSystem.entity;

public enum StaffRole {
    DOCTOR("Doctor"),
    NURSE("Nurse"),
    BLOOD_BANK_MANAGER("Blood Bank Manager"),
    RECEPTIONIST("Receptionist"),
    ADMIN("Admin");
    
    private final String displayName;
    
    StaffRole(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
    
    public static StaffRole fromDisplayName(String displayName) {
        for (StaffRole role : values()) {
            if (role.displayName.equals(displayName)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown staff role: " + displayName);
    }
}
