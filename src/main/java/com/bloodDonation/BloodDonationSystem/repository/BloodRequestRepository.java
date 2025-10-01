package com.bloodDonation.BloodDonationSystem.repository;

import com.bloodDonation.BloodDonationSystem.entity.BloodRequest;
import com.bloodDonation.BloodDonationSystem.entity.BloodType;
import com.bloodDonation.BloodDonationSystem.entity.EmergencyLevel;
import com.bloodDonation.BloodDonationSystem.entity.RequestStatus;
import com.bloodDonation.BloodDonationSystem.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BloodRequestRepository extends JpaRepository<BloodRequest, Long> {
    
    // Find requests by blood type
    List<BloodRequest> findByBloodType(BloodType bloodType);
    
    // Find requests by status
    List<BloodRequest> findByStatus(RequestStatus status);
    
    // Find requests by emergency level
    List<BloodRequest> findByEmergencyLevel(EmergencyLevel emergencyLevel);
    
    // Find requests by staff member
    List<BloodRequest> findByRequestedBy(Staff requestedBy);
    
    // Find requests by blood type and status
    List<BloodRequest> findByBloodTypeAndStatus(BloodType bloodType, RequestStatus status);
    
    // Find requests by emergency level and status
    List<BloodRequest> findByEmergencyLevelAndStatus(EmergencyLevel emergencyLevel, RequestStatus status);
    
    // Find requests within date range
    List<BloodRequest> findByRequestDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    // Find high priority requests (High emergency level and Pending status)
    @Query("SELECT br FROM BloodRequest br WHERE br.emergencyLevel = 'HIGH' AND br.status = 'PENDING' ORDER BY br.requestDate ASC")
    List<BloodRequest> findHighPriorityRequests();
    
    // Count requests by status
    long countByStatus(RequestStatus status);
    
    // Count requests by blood type
    long countByBloodType(BloodType bloodType);
    
    // Count requests by emergency level
    long countByEmergencyLevel(EmergencyLevel emergencyLevel);
    
    // Find recent requests (last 30 days)
    @Query("SELECT br FROM BloodRequest br WHERE br.requestDate >= :startDate ORDER BY br.requestDate DESC")
    List<BloodRequest> findRecentRequests(@Param("startDate") LocalDateTime startDate);
}
