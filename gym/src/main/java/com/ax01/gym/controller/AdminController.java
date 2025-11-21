/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ax01.gym.controller;

import com.ax01.gym.model.Account;
import com.ax01.gym.model.User;
import com.ax01.gym.repository.IAccountJpaRepository;
import com.ax01.gym.service.IAdminService;
import java.util.UUID;
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
@RequestMapping("/admins")
public class AdminController {

    private final IAdminService adminService;
    private final IAccountJpaRepository accountRepository;

    public AdminController(IAdminService adminService, IAccountJpaRepository accountRepository) {
        this.adminService = adminService;
        this.accountRepository = accountRepository;
    }

    // LISTAR
    @GetMapping
    public String index(Model model) {
        model.addAttribute("accounts", adminService.findAllAdmins());
        return "pages/adminList"; 
    }

    // CREAR
    @GetMapping("/new")
    public String create(Model model) {
        Account account = new Account();
        account.setUser(new User());
        
        model.addAttribute("account", account);
        model.addAttribute("isEdit", false);
        return "pages/adminForm";
    }
    
    // DETALLES
    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") UUID id, Model model) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
        model.addAttribute("account", account);
        return "pages/adminDetails"; 
    }
    
    // EDITAR
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") UUID id, Model model) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
        
        account.setPassword(""); 
        
        model.addAttribute("account", account);
        model.addAttribute("isEdit", true); 
        return "pages/adminForm";
    }

    // GUARDAR (CREATE & UPDATE)
    @PostMapping("/save")
    public String save(@ModelAttribute Account account) {
        
        if (account.getId() != null) {
            Account dbAccount = accountRepository.findById(account.getId())
                    .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
            
            if (account.getUser().getRole() == null) {
                account.getUser().setRole(dbAccount.getUser().getRole());
            }

            if (account.getPassword() == null || account.getPassword().trim().isEmpty()) {
                account.setPassword(dbAccount.getPassword());
            }
            
            account.setCreatedAt(dbAccount.getCreatedAt());
            account.getUser().setCreatedAt(dbAccount.getUser().getCreatedAt());
            
            if (account.getUser().getId() == null) {
                account.getUser().setId(dbAccount.getUser().getId());
            }
        }
        
        User user = account.getUser();
        
        adminService.registerNewAdmin(user, account);
        
        return "redirect:/admins/details/" + account.getId();
    }
    
    // ELIMINAR
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") java.util.UUID userId) {
        adminService.permanentlyDeleteUser(userId);
        return "redirect:/admins";
    }
}