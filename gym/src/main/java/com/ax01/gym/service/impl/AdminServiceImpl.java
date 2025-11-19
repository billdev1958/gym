/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ax01.gym.service.impl;

import com.ax01.gym.model.CatRole;
import com.ax01.gym.repository.IAccountJpaRepository;
import com.ax01.gym.repository.ICatRoleJpaRepository;
import com.ax01.gym.repository.IUsersJpaRepository;
import java.util.Optional;

/**
 *
 * @author bill
 */
public class AdminServiceImpl {

    private final IUsersJpaRepository userRepository;
    private final IAccountJpaRepository accountRepository;
    private final ICatRoleJpaRepository catRoleRepository;

    public AdminServiceImpl(IUsersJpaRepository userRepository, IAccountJpaRepository accountRepository, ICatRoleJpaRepository catRoleRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.catRoleRepository = catRoleRepository;
    }
    

}
