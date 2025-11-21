package com.ax01.gym.repository;

import com.ax01.gym.model.Account;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IAccountJpaRepository extends JpaRepository<Account, UUID> {

    Optional<Account> findByEmail(String email);

    Optional<Account> findByUserId(UUID userId);
    
    @Query("SELECT a FROM Account a JOIN FETCH a.user u WHERE u.role.name = ?1 AND u.deletedAt IS NULL")
    List<Account> findAllByUserRoleName(String roleName);
}
