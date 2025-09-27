package com.blooddonation.blood.controller;

import com.blooddonation.blood.model.Coordination;
import com.blooddonation.blood.repository.CoordinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/coordination")
public class CoordinationController {
    
    @Autowired
    private CoordinationRepository coordinationRepository;
    
    @GetMapping
    public String coordination(Model model) {
        List<Coordination> coordinations = coordinationRepository.findAll();
        model.addAttribute("coordinations", coordinations);
        return "coordination";
    }
    
    @PostMapping("/send")
    public String sendInformation(@RequestParam String recipient,
                                 @RequestParam String information) {
        Coordination coordination = new Coordination();
        coordination.setRecipient(recipient);
        coordination.setInformation(information);
        
        coordinationRepository.save(coordination);
        return "redirect:/coordination";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteCoordination(@PathVariable Long id) {
        coordinationRepository.deleteById(id);
        return "redirect:/coordination";
    }
}

