package com.bloodDonation.BloodDonationSystem.controller;

import com.bloodDonation.BloodDonationSystem.entity.Staff;
import com.bloodDonation.BloodDonationSystem.entity.StaffRole;
import com.bloodDonation.BloodDonationSystem.service.StaffService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/staff")
public class StaffController {
    
    @Autowired
    private StaffService staffService;
    
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("staff", new Staff());
        model.addAttribute("roles", StaffRole.values());
        return "admin_login";
    }
    
    @PostMapping("/login")
    public String loginStaff(@RequestParam String username, 
                           @RequestParam String password,
                           @RequestParam String role,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        try {
            StaffRole staffRole = StaffRole.fromDisplayName(role);
            Staff staff = staffService.authenticateStaff(username, password).orElse(null);
            
            if (staff != null && staff.getRole() == staffRole && staff.getIsActive()) {
                session.setAttribute("loggedInStaff", staff);
                redirectAttributes.addFlashAttribute("success", "Login successful!");
                
                // Redirect based on role
                switch (staffRole) {
                    case DOCTOR:
                        return "redirect:/doctor/dashboard";
                    case ADMIN:
                        return "redirect:/admin/dashboard";
                    default:
                        return "redirect:/staff/dashboard";
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "Invalid credentials or inactive account.");
                return "redirect:/staff/login";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Login failed: " + e.getMessage());
            return "redirect:/staff/login";
        }
    }
    
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("staff", new Staff());
        model.addAttribute("roles", StaffRole.values());
        return "admin_register";
    }
    
    @PostMapping("/register")
    public String registerStaff(@ModelAttribute Staff staff, 
                               @RequestParam String confirmPassword,
                               RedirectAttributes redirectAttributes) {
        try {
            // Validate password confirmation
            if (!staff.getPassword().equals(confirmPassword)) {
                redirectAttributes.addFlashAttribute("error", "Passwords do not match.");
                return "redirect:/staff/register";
            }
            
            // Check if username already exists
            if (staffService.isUsernameExists(staff.getUsername())) {
                redirectAttributes.addFlashAttribute("error", "Username already exists.");
                return "redirect:/staff/register";
            }
            
            // Check if email already exists
            if (staffService.isEmailExists(staff.getEmail())) {
                redirectAttributes.addFlashAttribute("error", "Email already exists.");
                return "redirect:/staff/register";
            }
            
            staffService.saveStaff(staff);
            redirectAttributes.addFlashAttribute("success", "Registration successful. Please log in.");
            return "redirect:/staff/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Registration failed: " + e.getMessage());
            return "redirect:/staff/register";
        }
    }
    
    @GetMapping("/dashboard")
    public String staffDashboard(HttpSession session, Model model) {
        Staff staff = (Staff) session.getAttribute("loggedInStaff");
        if (staff == null) {
            return "redirect:/staff/login";
        }
        
        model.addAttribute("staff", staff);
        return "staff_dashboard";
    }
    
    @GetMapping("/list")
    public String listStaff(Model model) {
        List<Staff> staffList = staffService.getAllStaff();
        model.addAttribute("staffList", staffList);
        return "staff_list";
    }
    
    @GetMapping("/search")
    public String searchStaff(@RequestParam(required = false) String name,
                             @RequestParam(required = false) String role,
                             Model model) {
        List<Staff> staffList;
        
        if (name != null && !name.isEmpty()) {
            staffList = staffService.searchStaffByName(name);
        } else if (role != null && !role.isEmpty()) {
            staffList = staffService.getStaffByRole(StaffRole.fromDisplayName(role));
        } else {
            staffList = staffService.getAllStaff();
        }
        
        model.addAttribute("staffList", staffList);
        model.addAttribute("roles", StaffRole.values());
        return "staff_list";
    }
    
    @GetMapping("/edit/{id}")
    public String editStaff(@PathVariable Long id, Model model) {
        Staff staff = staffService.getStaffById(id).orElse(null);
        if (staff == null) {
            return "redirect:/staff/list";
        }
        model.addAttribute("staff", staff);
        model.addAttribute("roles", StaffRole.values());
        return "staff_edit";
    }
    
    @PostMapping("/update")
    public String updateStaff(@ModelAttribute Staff staff, RedirectAttributes redirectAttributes) {
        try {
            staffService.updateStaff(staff);
            redirectAttributes.addFlashAttribute("success", "Staff updated successfully!");
            return "redirect:/staff/list";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Update failed: " + e.getMessage());
            return "redirect:/staff/edit/" + staff.getId();
        }
    }
    
    @GetMapping("/deactivate/{id}")
    public String deactivateStaff(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            staffService.deactivateStaff(id);
            redirectAttributes.addFlashAttribute("success", "Staff deactivated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Deactivation failed: " + e.getMessage());
        }
        return "redirect:/staff/list";
    }
    
    @PostMapping("/logout")
    public String logoutStaff(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
