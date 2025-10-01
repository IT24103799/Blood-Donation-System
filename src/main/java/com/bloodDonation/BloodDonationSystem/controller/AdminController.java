package com.bloodDonation.BloodDonationSystem.controller;

import com.bloodDonation.BloodDonationSystem.entity.BloodRequest;
import com.bloodDonation.BloodDonationSystem.entity.Donor;
import com.bloodDonation.BloodDonationSystem.entity.Staff;
import com.bloodDonation.BloodDonationSystem.service.BloodRequestService;
import com.bloodDonation.BloodDonationSystem.service.DonorService;
import com.bloodDonation.BloodDonationSystem.service.StaffService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private DonorService donorService;
    
    @Autowired
    private StaffService staffService;
    
    @Autowired
    private BloodRequestService bloodRequestService;
    
    @GetMapping("/dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        Staff staff = (Staff) session.getAttribute("loggedInStaff");
        if (staff == null || staff.getRole() != com.bloodDonation.BloodDonationSystem.entity.StaffRole.ADMIN) {
            return "redirect:/staff/login";
        }
        
        // Get statistics
        long totalDonors = donorService.getAllDonors().size();
        long totalStaff = staffService.getActiveStaffCount();
        long pendingRequests = bloodRequestService.getRequestCountByStatus(com.bloodDonation.BloodDonationSystem.entity.RequestStatus.PENDING);
        long highPriorityRequests = bloodRequestService.getHighPriorityRequests().size();
        
        model.addAttribute("staff", staff);
        model.addAttribute("totalDonors", totalDonors);
        model.addAttribute("totalStaff", totalStaff);
        model.addAttribute("pendingRequests", pendingRequests);
        model.addAttribute("highPriorityRequests", highPriorityRequests);
        
        // Get recent data
        List<Donor> recentDonors = donorService.getAllDonors().stream().limit(5).toList();
        List<BloodRequest> recentRequests = bloodRequestService.getRecentRequests(7);
        
        model.addAttribute("recentDonors", recentDonors);
        model.addAttribute("recentRequests", recentRequests);
        
        return "admin_dashboard";
    }
    
    @GetMapping("/donors")
    public String manageDonors(HttpSession session, Model model) {
        Staff staff = (Staff) session.getAttribute("loggedInStaff");
        if (staff == null || staff.getRole() != com.bloodDonation.BloodDonationSystem.entity.StaffRole.ADMIN) {
            return "redirect:/staff/login";
        }
        
        List<Donor> donors = donorService.getAllDonors();
        model.addAttribute("donors", donors);
        model.addAttribute("staff", staff);
        
        return "admin_donors";
    }
    
    @GetMapping("/staff")
    public String manageStaff(HttpSession session, Model model) {
        Staff staff = (Staff) session.getAttribute("loggedInStaff");
        if (staff == null || staff.getRole() != com.bloodDonation.BloodDonationSystem.entity.StaffRole.ADMIN) {
            return "redirect:/staff/login";
        }
        
        List<Staff> staffList = staffService.getAllStaff();
        model.addAttribute("staffList", staffList);
        model.addAttribute("currentStaff", staff);
        
        return "admin_staff";
    }
    
    @GetMapping("/requests")
    public String manageRequests(HttpSession session, Model model) {
        Staff staff = (Staff) session.getAttribute("loggedInStaff");
        if (staff == null || staff.getRole() != com.bloodDonation.BloodDonationSystem.entity.StaffRole.ADMIN) {
            return "redirect:/staff/login";
        }
        
        List<BloodRequest> requests = bloodRequestService.getAllBloodRequests();
        model.addAttribute("requests", requests);
        model.addAttribute("staff", staff);
        
        return "admin_requests";
    }
    
    @PostMapping("/approve-request/{id}")
    public String approveRequest(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        Staff staff = (Staff) session.getAttribute("loggedInStaff");
        if (staff == null || staff.getRole() != com.bloodDonation.BloodDonationSystem.entity.StaffRole.ADMIN) {
            return "redirect:/staff/login";
        }
        
        try {
            bloodRequestService.approveRequest(id);
            redirectAttributes.addFlashAttribute("success", "Request approved successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to approve request: " + e.getMessage());
        }
        
        return "redirect:/admin/requests";
    }
    
    @PostMapping("/reject-request/{id}")
    public String rejectRequest(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        Staff staff = (Staff) session.getAttribute("loggedInStaff");
        if (staff == null || staff.getRole() != com.bloodDonation.BloodDonationSystem.entity.StaffRole.ADMIN) {
            return "redirect:/staff/login";
        }
        
        try {
            bloodRequestService.rejectRequest(id);
            redirectAttributes.addFlashAttribute("success", "Request rejected successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to reject request: " + e.getMessage());
        }
        
        return "redirect:/admin/requests";
    }
    
    @PostMapping("/fulfill-request/{id}")
    public String fulfillRequest(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        Staff staff = (Staff) session.getAttribute("loggedInStaff");
        if (staff == null || staff.getRole() != com.bloodDonation.BloodDonationSystem.entity.StaffRole.ADMIN) {
            return "redirect:/staff/login";
        }
        
        try {
            bloodRequestService.fulfillRequest(id);
            redirectAttributes.addFlashAttribute("success", "Request fulfilled successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to fulfill request: " + e.getMessage());
        }
        
        return "redirect:/admin/requests";
    }
}
