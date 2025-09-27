package com.blooddonation.blood.repository;

import com.blooddonation.blood.model.Coordination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CoordinationRepository extends JpaRepository<Coordination, Long> {
    List<Coordination> findByRecipient(String recipient);
    List<Coordination> findByStatus(String status);
    List<Coordination> findByPriority(String priority);
}

