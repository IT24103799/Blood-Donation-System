package com.bloodDonation.BloodDonationSystem.repository;

import com.bloodDonation.BloodDonationSystem.entity.BloodType;
import com.bloodDonation.BloodDonationSystem.entity.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Long> {
    
    // Find donor by NIC
    Optional<Donor> findByNic(String nic);
    
    // Find donor by email
    Optional<Donor> findByEmail(String email);
    
    // Find donors by blood type
    List<Donor> findByBloodType(BloodType bloodType);
    
    // Find donors by contact number
    Optional<Donor> findByContactNumber(String contactNumber);
    
    // Search donors by name (first name or last name)
    @Query("SELECT d FROM Donor d WHERE LOWER(d.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(d.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Donor> findByNameContainingIgnoreCase(@Param("name") String name);
    
    // Find donors by donation location
    List<Donor> findByDonationLocation(String donationLocation);
    
    // Count donors by blood type
    long countByBloodType(BloodType bloodType);
}
