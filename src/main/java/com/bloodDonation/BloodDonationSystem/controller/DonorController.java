package com.bloodDonation.BloodDonationSystem.controller;

import com.bloodDonation.BloodDonationSystem.entity.BloodType;
import com.bloodDonation.BloodDonationSystem.entity.Donor;
import com.bloodDonation.BloodDonationSystem.service.DonorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/donor")
public class DonorController {
    
    @Autowired
    private DonorService donorService;
    
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("donor", new Donor());
        model.addAttribute("bloodTypes", BloodType.values());
        return "register";
    }
    
    @GetMapping("/register-detailed")
    public String showDetailedRegisterForm(Model model) {
        model.addAttribute("donor", new Donor());
        model.addAttribute("bloodTypes", BloodType.values());
        return "donor_register";
    }
    
    @PostMapping("/register")
    public String registerDonor(@ModelAttribute Donor donor, RedirectAttributes redirectAttributes) {
        try {
            // Check if NIC already exists
            if (donorService.isNicExists(donor.getNic())) {
                redirectAttributes.addFlashAttribute("error", "A donor with this NIC already exists.");
                return "redirect:/donor/register";
            }
            
            // Check if email already exists (if provided)
            if (donor.getEmail() != null && !donor.getEmail().isEmpty() && 
                donorService.isEmailExists(donor.getEmail())) {
                redirectAttributes.addFlashAttribute("error", "A donor with this email already exists.");
                return "redirect:/donor/register";
            }
            
            donorService.saveDonor(donor);
            return "redirect:/donor/success";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Registration failed: " + e.getMessage());
            return "redirect:/donor/register";
        }
    }
    
    @PostMapping("/register-detailed")
    public String registerDetailedDonor(@ModelAttribute Donor donor, RedirectAttributes redirectAttributes) {
        try {
            // Check if email already exists
            if (donorService.isEmailExists(donor.getEmail())) {
                redirectAttributes.addFlashAttribute("error", "A donor with this email already exists.");
                return "redirect:/donor/register-detailed";
            }
            
            donorService.saveDonor(donor);
            return "redirect:/donor/success";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Registration failed: " + e.getMessage());
            return "redirect:/donor/register-detailed";
        }
    }
    
    @GetMapping("/list")
    public String listDonors(Model model) {
        List<Donor> donors = donorService.getAllDonors();
        model.addAttribute("donors", donors);
        return "donor_list";
    }

    @GetMapping("/success")
    public String donorSuccess() {
        return "donor_success";
    }
    
    @GetMapping("/search")
    public String searchDonors(@RequestParam(required = false) String name, 
                              @RequestParam(required = false) String bloodType,
                              Model model) {
        List<Donor> donors;
        
        if (name != null && !name.isEmpty()) {
            donors = donorService.searchDonorsByName(name);
        } else if (bloodType != null && !bloodType.isEmpty()) {
            donors = donorService.getDonorsByBloodType(BloodType.fromDisplayName(bloodType));
        } else {
            donors = donorService.getAllDonors();
        }
        
        model.addAttribute("donors", donors);
        model.addAttribute("bloodTypes", BloodType.values());
        return "donor_list";
    }
    
    @GetMapping("/edit/{id}")
    public String editDonor(@PathVariable Long id, Model model) {
        Donor donor = donorService.getDonorById(id).orElse(null);
        if (donor == null) {
            return "redirect:/donor/list";
        }
        model.addAttribute("donor", donor);
        model.addAttribute("bloodTypes", BloodType.values());
        return "donor_edit";
    }
    
    @PostMapping("/update")
    public String updateDonor(@ModelAttribute Donor donor, RedirectAttributes redirectAttributes) {
        try {
            donorService.updateDonor(donor);
            redirectAttributes.addFlashAttribute("success", "Donor updated successfully!");
            return "redirect:/donor/list";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Update failed: " + e.getMessage());
            return "redirect:/donor/edit/" + donor.getId();
        }
    }
    
    @GetMapping("/delete/{id}")
    public String deleteDonor(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            donorService.deleteDonor(id);
            redirectAttributes.addFlashAttribute("success", "Donor deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Delete failed: " + e.getMessage());
        }
        return "redirect:/donor/list";
    }
}
