/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ax01.gym.service;

import com.ax01.gym.model.MembershipType;
import java.util.Optional;

/**
 *
 * @author bill
 */
public interface IMembershipsService {
    
    MembershipType updateMembership(MembershipType membershipType);

    Optional<MembershipType> findMembershipById(Integer id);
}