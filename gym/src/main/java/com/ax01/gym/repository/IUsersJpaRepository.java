package com.ax01.gym.repository;

import com.ax01.gym.model.User;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsersJpaRepository extends JpaRepository<User, UUID>{
    List<User> findByName(String name, String lastname1); 

}
