package com.bloodDonation.BloodDonationSystem.controller;

import com.bloodDonation.BloodDonationSystem.entity.BloodRequest;
import com.bloodDonation.BloodDonationSystem.entity.BloodType;
import com.bloodDonation.BloodDonationSystem.entity.EmergencyLevel;
import com.bloodDonation.BloodDonationSystem.entity.Staff;
import com.bloodDonation.BloodDonationSystem.service.BloodRequestService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/doctor")
public class DoctorController {
    
    @Autowired
    private BloodRequestService bloodRequestService;
    
    @GetMapping("/dashboard")
    public String doctorDashboard(HttpSession session, Model model) {
        Staff staff = (Staff) session.getAttribute("loggedInStaff");
        if (staff == null || staff.getRole() != com.bloodDonation.BloodDonationSystem.entity.StaffRole.DOCTOR) {
            return "redirect:/staff/login";
        }
        
        model.addAttribute("staff", staff);
        model.addAttribute("bloodTypes", BloodType.values());
        model.addAttribute("emergencyLevels", EmergencyLevel.values());
        
        // Get recent requests by this doctor
        List<BloodRequest> recentRequests = bloodRequestService.getBloodRequestsByStaff(staff);
        model.addAttribute("recentRequests", recentRequests);
        
        return "doctor_dashboard";
    }
    
    @PostMapping("/request-blood")
    public String requestBlood(@RequestParam String blood_type,
                              @RequestParam Integer amount,
                              @RequestParam String emergency_level,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        try {
            Staff staff = (Staff) session.getAttribute("loggedInStaff");
            if (staff == null || staff.getRole() != com.bloodDonation.BloodDonationSystem.entity.StaffRole.DOCTOR) {
                return "redirect:/staff/login";
            }
            
            BloodType bloodType = BloodType.fromDisplayName(blood_type);
            EmergencyLevel emergencyLevel = EmergencyLevel.fromDisplayName(emergency_level);
            
            BloodRequest bloodRequest = new BloodRequest(bloodType, amount, emergencyLevel, staff);
            bloodRequestService.saveBloodRequest(bloodRequest);
            
            redirectAttributes.addFlashAttribute("success", "Blood request submitted successfully!");
            return "redirect:/doctor/dashboard";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Request failed: " + e.getMessage());
            return "redirect:/doctor/dashboard";
        }
    }
    
    @GetMapping("/requests")
    public String viewRequests(HttpSession session, Model model) {
        Staff staff = (Staff) session.getAttribute("loggedInStaff");
        if (staff == null || staff.getRole() != com.bloodDonation.BloodDonationSystem.entity.StaffRole.DOCTOR) {
            return "redirect:/staff/login";
        }
        
        List<BloodRequest> requests = bloodRequestService.getBloodRequestsByStaff(staff);
        model.addAttribute("requests", requests);
        model.addAttribute("staff", staff);
        
        return "doctor_requests";
    }
}
