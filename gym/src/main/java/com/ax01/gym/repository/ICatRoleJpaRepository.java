package com.ax01.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ax01.gym.model.CatRole;
import java.util.Optional;

public interface ICatRoleJpaRepository extends JpaRepository<CatRole, Integer> {
@Override
Optional<CatRole> findById(Integer id);
Optional<CatRole> findByName(String name);
}
