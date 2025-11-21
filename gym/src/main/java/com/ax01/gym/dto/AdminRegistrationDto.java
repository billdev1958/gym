/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ax01.gym.dto;

/**
 *
 * @author bill
 */
public class AdminRegistrationDto {
    private String name;
    private String lastname1;
    private String lastname2;
    private String email;
    private String password;
    private String genre;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLastname1() { return lastname1; }
    public void setLastname1(String lastname1) { this.lastname1 = lastname1; }
    public String getLastname2() { return lastname2; }
    public void setLastname2(String lastname2) { this.lastname2 = lastname2; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
}