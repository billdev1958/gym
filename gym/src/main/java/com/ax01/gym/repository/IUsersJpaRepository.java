package com.ax01.gym.repository;

import com.ax01.gym.model.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsersJpaRepository extends JpaRepository<User, UUID>{

}
