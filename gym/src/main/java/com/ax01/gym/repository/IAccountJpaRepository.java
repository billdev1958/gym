package com.ax01.gym.repository;

import com.ax01.gym.model.Account;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAccountJpaRepository extends JpaRepository<Account, UUID> {

}
