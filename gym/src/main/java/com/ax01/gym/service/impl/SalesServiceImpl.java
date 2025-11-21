/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ax01.gym.service.impl;

import com.ax01.gym.model.Account;
import com.ax01.gym.model.CatRole;
import com.ax01.gym.model.ClientMembership;
import com.ax01.gym.model.MembershipType;
import com.ax01.gym.model.User;
import com.ax01.gym.repository.IAccountJpaRepository;
import com.ax01.gym.repository.ICatRoleJpaRepository;
import com.ax01.gym.repository.IClientJpaMembershipJpaRepository;
import com.ax01.gym.repository.IMembershipTypeJpaRepository;
import com.ax01.gym.repository.IUsersJpaRepository;
import com.ax01.gym.service.ISalesService;
import com.ax01.gym.utils.Constants.RoleEnum;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author bill
 */
@Service
public class SalesServiceImpl implements ISalesService {

    private final IUsersJpaRepository userRepository;
    private final IAccountJpaRepository accountRepository;
    private final IClientJpaMembershipJpaRepository clientMembershipRepository;
    private final ICatRoleJpaRepository catRoleRepository;
    private final IMembershipTypeJpaRepository membershipTypeRepository; 
    private final PasswordEncoder passwordEncoder;

    public SalesServiceImpl(IUsersJpaRepository userRepository, 
                            IAccountJpaRepository accountRepository, 
                            IClientJpaMembershipJpaRepository clientMembershipRepository, 
                            ICatRoleJpaRepository catRoleRepository, 
                            IMembershipTypeJpaRepository membershipTypeRepository,
                            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.clientMembershipRepository = clientMembershipRepository;
        this.catRoleRepository = catRoleRepository;
        this.membershipTypeRepository = membershipTypeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private Optional<CatRole> findRoleByName(String roleName) {
        return catRoleRepository.findByName(roleName); 
    }

    @Override
    @Transactional
    public User registerNewClientAndPurchase(User user, Account account, Integer membershipTypeId) {
        
        MembershipType type = membershipTypeRepository.findById(membershipTypeId)
                .orElseThrow(() -> new RuntimeException("El tipo de membresía seleccionado no existe."));
        

        CatRole clientRole = findRoleByName(RoleEnum.CLIENTE.getName())
                .orElseThrow(() -> new RuntimeException("Error: El rol CLIENTE no existe."));
        
        user.setRole(clientRole);
        
        if(user.getCreatedAt() == null) user.setCreatedAt(Instant.now());
        
        User savedUser = userRepository.save(user);

        account.setUser(savedUser); 
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        if(account.getCreatedAt() == null) account.setCreatedAt(Instant.now());
        
        accountRepository.save(account);

        ClientMembership newMembership = new ClientMembership();
        newMembership.setUser(savedUser);
        
        newMembership.setMembershipType(type); 
        
        LocalDate startDate = LocalDate.now();
        newMembership.setStartDate(startDate);
        
        newMembership.setEndDate(startDate.plusDays(type.getDurationDays())); 

        if(newMembership.getCreatedAt() == null) newMembership.setCreatedAt(Instant.now());

        clientMembershipRepository.save(newMembership);

        return savedUser;
    }

@Override
    @Transactional
    public ClientMembership renewClientMembership(UUID userId, Integer membershipTypeId) {
        
        ClientMembership existingMembership = clientMembershipRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("No se encontró la membresía del cliente."));

        LocalDate today = LocalDate.now();

        if (!existingMembership.getEndDate().isBefore(today)) {
            throw new RuntimeException("La membresía aún está vigente. Solo se puede renovar después de su vencimiento (" + existingMembership.getEndDate() + ").");
        }

        MembershipType newType = membershipTypeRepository.findById(membershipTypeId)
                .orElseThrow(() -> new RuntimeException("El tipo de membresía no existe."));

        LocalDate renewalStart = today;
        LocalDate renewalEnd = renewalStart.plusDays(newType.getDurationDays());

        existingMembership.setMembershipType(newType);
        existingMembership.setStartDate(renewalStart);
        existingMembership.setEndDate(renewalEnd);
        existingMembership.setDeletedAt(null); 
        existingMembership.setUpdatedAt(Instant.now());
        
        return clientMembershipRepository.save(existingMembership);
    }

    @Override
    @Transactional
    public ClientMembership updateClientMembership(ClientMembership membership) {
        if (membership.getId() == null) {
            throw new IllegalArgumentException("Se requiere el ID de la membresía para actualizar.");
        }
        membership.setUpdatedAt(Instant.now());
        return clientMembershipRepository.save(membership);
    } 

@Override
    @Transactional
    public void cancelClientMembership(UUID userId) {
        ClientMembership membership = clientMembershipRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("No se encontró la membresía a cancelar."));
        
        membership.setDeletedAt(Instant.now());
        
        membership.setEndDate(LocalDate.now().minusDays(1)); 
        
        clientMembershipRepository.save(membership);
    }
    
    @Override
    public Optional<ClientMembership> findMembershipByUserId(UUID userId) {
        return clientMembershipRepository.findByUserId(userId);
    }

    @Override
    public List<MembershipType> findAllMembershipTypes() {
        return membershipTypeRepository.findAll();
    }

    @Override
    public BigDecimal getMembershipCost(Integer membershipTypeId) {
        return membershipTypeRepository.findById(membershipTypeId)
                .map(MembershipType::getCost)
                .orElseThrow(() -> new RuntimeException("Tipo de membresía no encontrado para obtener el costo."));
    }
}