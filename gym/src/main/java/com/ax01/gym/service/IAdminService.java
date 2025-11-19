/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ax01.gym.service;

import com.ax01.gym.model.Account;
import com.ax01.gym.model.CatRole;
import com.ax01.gym.model.User;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author bill
 */
public interface IAdminService {
    
    User registerNewAdmin(User user, Account account);
    
    User updateAdmin(User user, Account account); 
    
    void permanentlyDeleteUser(UUID userId);
    
    List<User> findUsersByNameAndLastname(String name, String lastname1);

    List<User> findAllUsers();
    
    List<CatRole>findAllRoles();

}
