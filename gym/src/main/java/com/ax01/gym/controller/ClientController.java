/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ax01.gym.controller;

import com.ax01.gym.model.Account;
import com.ax01.gym.model.ClientMembership;
import com.ax01.gym.repository.IAccountJpaRepository;
import com.ax01.gym.repository.IClientJpaMembershipJpaRepository;
import com.ax01.gym.service.ISalesService;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author bill
 */
@Controller
@RequestMapping("/clients")
public class ClientController {

    private final IClientJpaMembershipJpaRepository clientMembershipRepository;
    private final IAccountJpaRepository accountRepository;
    private final ISalesService salesService;

    public ClientController(IClientJpaMembershipJpaRepository clientMembershipRepository, 
                            IAccountJpaRepository accountRepository,
                            ISalesService salesService) {
        this.clientMembershipRepository = clientMembershipRepository;
        this.accountRepository = accountRepository;
        this.salesService = salesService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("clients", clientMembershipRepository.findAll());
        return "pages/clientList";
    }

    @GetMapping("/details/{userId}")
    public String details(@PathVariable("userId") UUID userId, Model model) {
        ClientMembership membership = clientMembershipRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Membresía no encontrada"));
        
        Account account = accountRepository.findByUserId(userId).orElse(null);

        model.addAttribute("membership", membership);
        model.addAttribute("account", account);
        
        return "pages/clientDetails";
    }
    
    @PostMapping("/delete/{userId}")
    public String delete(@PathVariable("userId") UUID userId) {
        salesService.cancelClientMembership(userId);
        return "redirect:/clients";
    }
}