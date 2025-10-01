package com.bloodDonation.BloodDonationSystem.service;

import com.bloodDonation.BloodDonationSystem.entity.BloodRequest;
import com.bloodDonation.BloodDonationSystem.entity.BloodType;
import com.bloodDonation.BloodDonationSystem.entity.EmergencyLevel;
import com.bloodDonation.BloodDonationSystem.entity.RequestStatus;
import com.bloodDonation.BloodDonationSystem.entity.Staff;
import com.bloodDonation.BloodDonationSystem.repository.BloodRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BloodRequestService {
    
    @Autowired
    private BloodRequestRepository bloodRequestRepository;
    
    public BloodRequest saveBloodRequest(BloodRequest bloodRequest) {
        return bloodRequestRepository.save(bloodRequest);
    }
    
    public List<BloodRequest> getAllBloodRequests() {
        return bloodRequestRepository.findAll();
    }
    
    public Optional<BloodRequest> getBloodRequestById(Long id) {
        return bloodRequestRepository.findById(id);
    }
    
    public List<BloodRequest> getBloodRequestsByBloodType(BloodType bloodType) {
        return bloodRequestRepository.findByBloodType(bloodType);
    }
    
    public List<BloodRequest> getBloodRequestsByStatus(RequestStatus status) {
        return bloodRequestRepository.findByStatus(status);
    }
    
    public List<BloodRequest> getBloodRequestsByEmergencyLevel(EmergencyLevel emergencyLevel) {
        return bloodRequestRepository.findByEmergencyLevel(emergencyLevel);
    }
    
    public List<BloodRequest> getBloodRequestsByStaff(Staff staff) {
        return bloodRequestRepository.findByRequestedBy(staff);
    }
    
    public List<BloodRequest> getBloodRequestsByBloodTypeAndStatus(BloodType bloodType, RequestStatus status) {
        return bloodRequestRepository.findByBloodTypeAndStatus(bloodType, status);
    }
    
    public List<BloodRequest> getHighPriorityRequests() {
        return bloodRequestRepository.findHighPriorityRequests();
    }
    
    public List<BloodRequest> getRecentRequests(int days) {
        LocalDateTime startDate = LocalDateTime.now().minusDays(days);
        return bloodRequestRepository.findRecentRequests(startDate);
    }
    
    public long getRequestCountByStatus(RequestStatus status) {
        return bloodRequestRepository.countByStatus(status);
    }
    
    public long getRequestCountByBloodType(BloodType bloodType) {
        return bloodRequestRepository.countByBloodType(bloodType);
    }
    
    public long getRequestCountByEmergencyLevel(EmergencyLevel emergencyLevel) {
        return bloodRequestRepository.countByEmergencyLevel(emergencyLevel);
    }
    
    public BloodRequest updateBloodRequest(BloodRequest bloodRequest) {
        return bloodRequestRepository.save(bloodRequest);
    }
    
    public void deleteBloodRequest(Long id) {
        bloodRequestRepository.deleteById(id);
    }
    
    public BloodRequest approveRequest(Long id) {
        Optional<BloodRequest> request = bloodRequestRepository.findById(id);
        if (request.isPresent()) {
            request.get().setStatus(RequestStatus.APPROVED);
            return bloodRequestRepository.save(request.get());
        }
        return null;
    }
    
    public BloodRequest rejectRequest(Long id) {
        Optional<BloodRequest> request = bloodRequestRepository.findById(id);
        if (request.isPresent()) {
            request.get().setStatus(RequestStatus.REJECTED);
            return bloodRequestRepository.save(request.get());
        }
        return null;
    }
    
    public BloodRequest fulfillRequest(Long id) {
        Optional<BloodRequest> request = bloodRequestRepository.findById(id);
        if (request.isPresent()) {
            request.get().setStatus(RequestStatus.FULFILLED);
            return bloodRequestRepository.save(request.get());
        }
        return null;
    }
}
