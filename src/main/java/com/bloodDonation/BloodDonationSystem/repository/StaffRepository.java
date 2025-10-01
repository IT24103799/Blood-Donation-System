package com.bloodDonation.BloodDonationSystem.repository;

import com.bloodDonation.BloodDonationSystem.entity.Staff;
import com.bloodDonation.BloodDonationSystem.entity.StaffRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    
    // Find staff by username
    Optional<Staff> findByUsername(String username);
    
    // Find staff by email
    Optional<Staff> findByEmail(String email);
    
    // Find staff by role
    List<Staff> findByRole(StaffRole role);
    
    // Find active staff
    List<Staff> findByIsActiveTrue();
    
    // Find staff by role and active status
    List<Staff> findByRoleAndIsActiveTrue(StaffRole role);
    
    // Authenticate staff (username and password)
    Optional<Staff> findByUsernameAndPassword(String username, String password);
    
    // Search staff by name
    @Query("SELECT s FROM Staff s WHERE LOWER(s.fullName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Staff> findByNameContainingIgnoreCase(@Param("name") String name);
    
    // Count staff by role
    long countByRole(StaffRole role);
    
    // Count active staff
    long countByIsActiveTrue();
}
