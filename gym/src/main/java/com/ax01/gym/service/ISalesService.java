/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ax01.gym.service;

import com.ax01.gym.model.Account;
import com.ax01.gym.model.ClientMembership;
import com.ax01.gym.model.MembershipType;
import com.ax01.gym.model.User;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author bill
 */
public interface ISalesService {

    User registerNewClientAndPurchase(User user, Account account, Integer membershipTypeId);

    ClientMembership renewClientMembership(java.util.UUID userId, Integer membershipTypeId);
    
    void cancelClientMembership(UUID userId); 

    Optional<ClientMembership> findMembershipByUserId(UUID userId);
    
    List<MembershipType> findAllMembershipTypes();

    BigDecimal getMembershipCost(Integer membershipTypeId);

}
