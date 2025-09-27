package com.blooddonation.blood.controller;

import com.blooddonation.blood.model.Appointment;
import com.blooddonation.blood.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    @GetMapping
    public String appointmentManagement(Model model) {
        List<Appointment> appointments = appointmentRepository.findAll();
        model.addAttribute("appointments", appointments);
        return "appointment_management";
    }
    
    @PostMapping("/save")
    public String saveAppointment(@RequestParam String donorName, 
                                 @RequestParam String date, 
                                 @RequestParam String time) {
        Appointment appointment = new Appointment();
        appointment.setDonorName(donorName);
        appointment.setDate(LocalDate.parse(date));
        appointment.setTime(LocalTime.parse(time));
        
        appointmentRepository.save(appointment);
        return "redirect:/appointments";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        appointmentRepository.deleteById(id);
        return "redirect:/appointments";
    }
    
    @GetMapping("/edit/{id}")
    public String editAppointment(@PathVariable Long id, Model model) {
        Appointment appointment = appointmentRepository.findById(id).orElse(null);
        if (appointment != null) {
            model.addAttribute("appointment", appointment);
        }
        return "appointment_edit";
    }
    
    @PostMapping("/update")
    public String updateAppointment(@RequestParam Long id,
                                   @RequestParam String donorName,
                                   @RequestParam String date,
                                   @RequestParam String time) {
        Appointment appointment = appointmentRepository.findById(id).orElse(null);
        if (appointment != null) {
            appointment.setDonorName(donorName);
            appointment.setDate(LocalDate.parse(date));
            appointment.setTime(LocalTime.parse(time));
            appointmentRepository.save(appointment);
        }
        return "redirect:/appointments";
    }
}

