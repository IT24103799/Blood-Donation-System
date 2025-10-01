package com.bloodDonation.BloodDonationSystem.service;

import com.bloodDonation.BloodDonationSystem.entity.Staff;
import com.bloodDonation.BloodDonationSystem.entity.StaffRole;
import com.bloodDonation.BloodDonationSystem.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffService {
    
    @Autowired
    private StaffRepository staffRepository;
    
    public Staff saveStaff(Staff staff) {
        return staffRepository.save(staff);
    }
    
    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }
    
    public Optional<Staff> getStaffById(Long id) {
        return staffRepository.findById(id);
    }
    
    public Optional<Staff> getStaffByUsername(String username) {
        return staffRepository.findByUsername(username);
    }
    
    public Optional<Staff> getStaffByEmail(String email) {
        return staffRepository.findByEmail(email);
    }
    
    public List<Staff> getStaffByRole(StaffRole role) {
        return staffRepository.findByRole(role);
    }
    
    public List<Staff> getActiveStaff() {
        return staffRepository.findByIsActiveTrue();
    }
    
    public List<Staff> getActiveStaffByRole(StaffRole role) {
        return staffRepository.findByRoleAndIsActiveTrue(role);
    }
    
    public Optional<Staff> authenticateStaff(String username, String password) {
        return staffRepository.findByUsernameAndPassword(username, password);
    }
    
    public List<Staff> searchStaffByName(String name) {
        return staffRepository.findByNameContainingIgnoreCase(name);
    }
    
    public long getStaffCountByRole(StaffRole role) {
        return staffRepository.countByRole(role);
    }
    
    public long getActiveStaffCount() {
        return staffRepository.countByIsActiveTrue();
    }
    
    public Staff updateStaff(Staff staff) {
        return staffRepository.save(staff);
    }
    
    public void deleteStaff(Long id) {
        staffRepository.deleteById(id);
    }
    
    public void deactivateStaff(Long id) {
        Optional<Staff> staff = staffRepository.findById(id);
        if (staff.isPresent()) {
            staff.get().setIsActive(false);
            staffRepository.save(staff.get());
        }
    }
    
    public boolean isUsernameExists(String username) {
        return staffRepository.findByUsername(username).isPresent();
    }
    
    public boolean isEmailExists(String email) {
        return staffRepository.findByEmail(email).isPresent();
    }
}
