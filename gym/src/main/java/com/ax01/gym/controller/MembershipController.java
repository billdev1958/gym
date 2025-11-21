/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ax01.gym.controller;

import com.ax01.gym.model.MembershipType;
import com.ax01.gym.repository.IMembershipTypeJpaRepository;
import com.ax01.gym.service.IMembershipsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author bill
 */
@Controller
@RequestMapping("/memberships")
public class MembershipController {

    private final IMembershipTypeJpaRepository membershipRepository;
    private final IMembershipsService membershipsService;

    public MembershipController(IMembershipTypeJpaRepository membershipRepository, IMembershipsService membershipsService) {
        this.membershipRepository = membershipRepository;
        this.membershipsService = membershipsService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("memberships", membershipRepository.findAll());
        return "pages/membershipsList";
    }
    
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Integer id, Model model) {
        MembershipType membership = membershipsService.findMembershipById(id)
                .orElseThrow(() -> new RuntimeException("Membresía no encontrada"));
        
        model.addAttribute("membership", membership);
        return "pages/membershipForm";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MembershipType membership) {
        membershipsService.updateMembership(membership);
        return "redirect:/memberships";
    }
    
}