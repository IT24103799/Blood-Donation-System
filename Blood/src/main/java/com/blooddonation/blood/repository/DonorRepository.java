package com.blooddonation.blood.repository;

import com.blooddonation.blood.model.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Long> {
    List<Donor> findByNameContainingIgnoreCase(String name);
    Optional<Donor> findByEmail(String email);
    List<Donor> findByBloodType(String bloodType);
}

