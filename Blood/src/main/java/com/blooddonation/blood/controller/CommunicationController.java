package com.blooddonation.blood.controller;

import com.blooddonation.blood.model.Communication;
import com.blooddonation.blood.repository.CommunicationRepository;
import com.blooddonation.blood.repository.DonorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/communications")
public class CommunicationController {
    
    @Autowired
    private CommunicationRepository communicationRepository;
    
    @Autowired
    private DonorRepository donorRepository;
    
    @GetMapping
    public String donorCommunication(Model model) {
        List<Communication> communications = communicationRepository.findAll();
        List<com.blooddonation.blood.model.Donor> donors = donorRepository.findAll();
        model.addAttribute("communications", communications);
        model.addAttribute("donors", donors);
        return "donor_communication";
    }
    
    @PostMapping("/send")
    public String sendMessage(@RequestParam String donorName,
                             @RequestParam String message) {
        Communication communication = new Communication();
        communication.setDonorName(donorName);
        communication.setMessage(message);
        
        communicationRepository.save(communication);
        return "redirect:/communications";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteCommunication(@PathVariable Long id) {
        communicationRepository.deleteById(id);
        return "redirect:/communications";
    }
}

