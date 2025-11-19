package com.ax01.gym.repository;

import com.ax01.gym.model.ClientMembership;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClientJpaMembershipJpaRepository extends JpaRepository<ClientMembership, UUID> {

    Optional<ClientMembership> findByUserId(UUID userId);

}
