/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ax01.gym.service.impl;

import com.ax01.gym.model.Account;
import com.ax01.gym.model.CatRole;
import com.ax01.gym.model.User;
import com.ax01.gym.repository.IAccountJpaRepository;
import com.ax01.gym.repository.ICatRoleJpaRepository;
import com.ax01.gym.repository.IUsersJpaRepository;
import com.ax01.gym.service.IAdminService;
import com.ax01.gym.utils.Constants.RoleEnum;
import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author bill
 */
public class AdminServiceImpl implements IAdminService{

    private final IUsersJpaRepository userRepository;
    private final IAccountJpaRepository accountRepository;
    private final ICatRoleJpaRepository catRoleRepository;

    public AdminServiceImpl(IUsersJpaRepository userRepository, IAccountJpaRepository accountRepository, ICatRoleJpaRepository catRoleRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.catRoleRepository = catRoleRepository;
    }
    
    private Optional<CatRole> findRoleByName(String roleName) {
        return catRoleRepository.findByName(roleName); 
    }
    
    @Override
    @Transactional
    public User registerNewAdmin(User user, Account account) {
        CatRole adminRole = findRoleByName(RoleEnum.ADMINISTRADOR.getName())
                .orElseThrow(() -> new RuntimeException("Error: El rol ADMINISTRADOR no existe en el sistema."));
        user.setRole(adminRole);
        
        User savedUser = userRepository.save(user);
        
        account.setUser(savedUser);
        accountRepository.save(account);
        
        return savedUser;
    }
    
    @Override
    @Transactional
    public User updateAdmin(User user, Account account) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("Se requiere el ID del administrador para actualizar la información personal.");
        }
        
        user.setUpdatedAt(Instant.now());
        User savedUser = userRepository.save(user);

        Account existingAccount = accountRepository.findByUserId(savedUser.getId())
            .orElseThrow(() -> new RuntimeException("Cuenta de administrador no encontrada para el ID de usuario: " + savedUser.getId()));

        existingAccount.setEmail(account.getEmail());
        existingAccount.setPassword(account.getPassword());
        existingAccount.setUpdatedAt(Instant.now());
        
        accountRepository.save(existingAccount);

        return savedUser;
    }
    
    @Override
    @Transactional
    public void permanentlyDeleteUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado para eliminación permanente."));
        
        Optional<Account> account = accountRepository.findByUserId(userId); 
        account.ifPresent(accountRepository::delete);
        
        userRepository.delete(user);
    }
    
    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
    
    @Override
    public List<User> findUsersByNameAndLastname(String name, String lastname1) {
        return userRepository.findByName(name, lastname1);
    }
    
    @Override
    public List<CatRole> findAllRoles() {
        return catRoleRepository.findAll();
    }

}
