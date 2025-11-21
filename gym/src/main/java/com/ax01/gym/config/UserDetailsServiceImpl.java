/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ax01.gym.config;

import com.ax01.gym.model.Account;
import com.ax01.gym.repository.IAccountJpaRepository;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author bill
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IAccountJpaRepository accountRepository;

    public UserDetailsServiceImpl(IAccountJpaRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        String roleName = account.getUser().getRole().getName(); 
        
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + roleName);

        return new org.springframework.security.core.userdetails.User(
                account.getEmail(),
                account.getPassword(), 
                true,
                true,
                true,
                true,
                Collections.singletonList(authority)
        );
    }
}