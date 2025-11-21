/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ax01.gym.service.impl;

import com.ax01.gym.model.MembershipType;
import com.ax01.gym.repository.IMembershipTypeJpaRepository;
import com.ax01.gym.service.IMembershipsService;
import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 *
 * @author bill
 */
@Service
public class MembershipsServiceImpl implements IMembershipsService {

    private final IMembershipTypeJpaRepository membershipRepository;

    public MembershipsServiceImpl(IMembershipTypeJpaRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    @Override
    @Transactional
    public MembershipType updateMembership(MembershipType membershipType) {
        if (membershipType.getId() == null || !membershipRepository.existsById(membershipType.getId())) {
            throw new RuntimeException("No se puede editar: La membresía no existe.");
        }

        membershipType.setUpdatedAt(Instant.now());
        
        return membershipRepository.save(membershipType);
    }

    @Override
    public Optional<MembershipType> findMembershipById(Integer id) {
        return membershipRepository.findById(id);
    }
}
