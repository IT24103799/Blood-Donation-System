package com.blooddonation.blood.repository;

import com.blooddonation.blood.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDonorNameContainingIgnoreCase(String donorName);
    List<Appointment> findByDate(LocalDate date);
    List<Appointment> findByStatus(String status);
    List<Appointment> findByDateBetween(LocalDate startDate, LocalDate endDate);
}

