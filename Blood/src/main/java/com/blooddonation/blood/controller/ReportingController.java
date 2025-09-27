package com.blooddonation.blood.controller;

import com.blooddonation.blood.model.Appointment;
import com.blooddonation.blood.model.Donor;
import com.blooddonation.blood.repository.AppointmentRepository;
import com.blooddonation.blood.repository.DonorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/reports")
public class ReportingController {
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    @Autowired
    private DonorRepository donorRepository;
    
    @GetMapping
    public String reportingTracking(Model model) {
        return "reporting_tracking";
    }
    
    @PostMapping("/generate")
    public String generateReport(@RequestParam String reportType, Model model) {
        switch (reportType) {
            case "Donor Attendance":
                List<Appointment> appointments = appointmentRepository.findAll();
                model.addAttribute("appointments", appointments);
                model.addAttribute("reportType", "Donor Attendance");
                break;
            case "Donation History":
                List<Donor> donors = donorRepository.findAll();
                model.addAttribute("donors", donors);
                model.addAttribute("reportType", "Donation History");
                break;
            case "Feedback Summary":
                // For now, we'll use communications as feedback
                model.addAttribute("reportType", "Feedback Summary");
                break;
        }
        return "reporting_tracking";
    }
}

