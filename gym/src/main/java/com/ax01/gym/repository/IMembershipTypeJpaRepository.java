package com.ax01.gym.repository;

import com.ax01.gym.model.MembershipType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMembershipTypeJpaRepository extends JpaRepository<MembershipType, Integer> {

    Optional<MembershipType> findByName(String name);
}
