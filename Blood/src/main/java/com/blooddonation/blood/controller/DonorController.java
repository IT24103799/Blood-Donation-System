package com.blooddonation.blood.controller;

import com.blooddonation.blood.model.Donor;
import com.blooddonation.blood.repository.DonorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/donors")
public class DonorController {
    
    @Autowired
    private DonorRepository donorRepository;
    
    @GetMapping
    public String donorProfiles(Model model) {
        List<Donor> donors = donorRepository.findAll();
        model.addAttribute("donors", donors);
        return "donor_profile_handling";
    }
    
    @PostMapping("/save")
    public String saveDonor(@RequestParam String name,
                           @RequestParam String email,
                           @RequestParam String phone,
                           @RequestParam(required = false) String address,
                           @RequestParam(required = false) String bloodType) {
        Donor donor = new Donor();
        donor.setName(name);
        donor.setEmail(email);
        donor.setPhone(phone);
        donor.setAddress(address);
        donor.setBloodType(bloodType);
        
        donorRepository.save(donor);
        return "redirect:/donors";
    }
    
    @PostMapping("/update")
    public String updateDonor(@RequestParam Long id,
                             @RequestParam String name,
                             @RequestParam String email,
                             @RequestParam String phone,
                             @RequestParam(required = false) String address,
                             @RequestParam(required = false) String bloodType) {
        Donor donor = donorRepository.findById(id).orElse(null);
        if (donor != null) {
            donor.setName(name);
            donor.setEmail(email);
            donor.setPhone(phone);
            donor.setAddress(address);
            donor.setBloodType(bloodType);
            donorRepository.save(donor);
        }
        return "redirect:/donors";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteDonor(@PathVariable Long id) {
        donorRepository.deleteById(id);
        return "redirect:/donors";
    }
    
    @GetMapping("/edit/{id}")
    public String editDonor(@PathVariable Long id, Model model) {
        Donor donor = donorRepository.findById(id).orElse(null);
        if (donor != null) {
            model.addAttribute("donor", donor);
        }
        return "donor_edit";
    }
}

