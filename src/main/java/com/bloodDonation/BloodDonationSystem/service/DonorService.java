package com.bloodDonation.BloodDonationSystem.service;

import com.bloodDonation.BloodDonationSystem.entity.BloodType;
import com.bloodDonation.BloodDonationSystem.entity.Donor;
import com.bloodDonation.BloodDonationSystem.repository.DonorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonorService {
    
    @Autowired
    private DonorRepository donorRepository;
    
    public Donor saveDonor(Donor donor) {
        return donorRepository.save(donor);
    }
    
    public List<Donor> getAllDonors() {
        return donorRepository.findAll();
    }
    
    public Optional<Donor> getDonorById(Long id) {
        return donorRepository.findById(id);
    }
    
    public Optional<Donor> getDonorByNic(String nic) {
        return donorRepository.findByNic(nic);
    }
    
    public Optional<Donor> getDonorByEmail(String email) {
        return donorRepository.findByEmail(email);
    }
    
    public List<Donor> getDonorsByBloodType(BloodType bloodType) {
        return donorRepository.findByBloodType(bloodType);
    }
    
    public List<Donor> searchDonorsByName(String name) {
        return donorRepository.findByNameContainingIgnoreCase(name);
    }
    
    public List<Donor> getDonorsByLocation(String location) {
        return donorRepository.findByDonationLocation(location);
    }
    
    public long getDonorCountByBloodType(BloodType bloodType) {
        return donorRepository.countByBloodType(bloodType);
    }
    
    public Donor updateDonor(Donor donor) {
        return donorRepository.save(donor);
    }
    
    public void deleteDonor(Long id) {
        donorRepository.deleteById(id);
    }
    
    public boolean isNicExists(String nic) {
        return donorRepository.findByNic(nic).isPresent();
    }
    
    public boolean isEmailExists(String email) {
        return donorRepository.findByEmail(email).isPresent();
    }
}
