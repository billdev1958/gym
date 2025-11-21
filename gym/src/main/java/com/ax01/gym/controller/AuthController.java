/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ax01.gym.controller;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author bill
 */
@Controller
public class AuthController {

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, 
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model,
                        Principal principal) {
        
        if (principal != null) {
            return "redirect:/";
        }

        if (error != null) {
            model.addAttribute("errorMessage", "Usuario o contraseña incorrectos.");
        }
        
        if (logout != null) {
            model.addAttribute("infoMessage", "Has cerrado sesión correctamente.");
        }

        return "pages/login";
    }
}