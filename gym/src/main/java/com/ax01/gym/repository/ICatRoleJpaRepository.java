package com.ax01.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ax01.gym.model.CatRole;

public interface ICatRoleJpaRepository extends JpaRepository<CatRole, Integer> {

}
