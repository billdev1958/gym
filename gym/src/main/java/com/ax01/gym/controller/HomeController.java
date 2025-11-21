/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ax01.gym.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author bill
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Model model) {
        return "pages/index";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        return "pages/admin";
    }

    @GetMapping("/sales")
    public String sales(Model model) {
        return "pages/sales";
    }
}