/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ax01.gym.controller;

import com.ax01.gym.model.Account;
import com.ax01.gym.model.User;
import com.ax01.gym.service.ISalesService;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author bill
 */
@Controller
@RequestMapping("/sales")
public class SalesController {

    private final ISalesService salesService;

    public SalesController(ISalesService salesService) {
        this.salesService = salesService;
    }

    @GetMapping("/new") 
    public String newClientForm(Model model) {
        Account account = new Account();
        account.setUser(new User());
        
        model.addAttribute("account", account);
        
        model.addAttribute("membershipTypes", salesService.findAllMembershipTypes());
        
        return "pages/salesForm";
    }

    // GUARDAR NUEVO CLIENTE Y VENTA
    @PostMapping("/save")
    public String saveNewClient(@ModelAttribute Account account, 
                                @RequestParam("membershipTypeId") Integer membershipTypeId) {
        
        User user = account.getUser();
        
        salesService.registerNewClientAndPurchase(user, account, membershipTypeId);
        
        return "redirect:/clients";
    }
    
    @GetMapping("/renew/{id}")
    public String renewClientForm(@PathVariable("id") UUID userId, Model model) {
        
        salesService.findMembershipByUserId(userId).ifPresent(membership -> {
            model.addAttribute("clientName", membership.getUser().getName() + " " + membership.getUser().getLastname1());
            model.addAttribute("currentEndDate", membership.getEndDate());
        });

        model.addAttribute("userId", userId);
        model.addAttribute("membershipTypes", salesService.findAllMembershipTypes());
        
        return "pages/renew";
    }

    // Procesar la Renovación
    @PostMapping("/renew")
    public String saveRenewal(@RequestParam("userId") UUID userId, 
                              @RequestParam("membershipTypeId") Integer membershipTypeId,
                              RedirectAttributes redirectAttributes) {
        try {
            salesService.renewClientMembership(userId, membershipTypeId);
            redirectAttributes.addFlashAttribute("successMessage", "Renovación exitosa.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/clients/details/" + userId;
        }
        
        return "redirect:/clients/details/" + userId;
    }
}