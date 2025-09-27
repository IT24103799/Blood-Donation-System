package com.blooddonation.blood.repository;

import com.blooddonation.blood.model.Communication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommunicationRepository extends JpaRepository<Communication, Long> {
    List<Communication> findByDonorNameContainingIgnoreCase(String donorName);
    List<Communication> findByCommunicationType(String communicationType);
    List<Communication> findByStatus(String status);
}

