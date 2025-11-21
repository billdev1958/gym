package com.ax01.gym.repository;

import com.ax01.gym.model.User;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IUsersJpaRepository extends JpaRepository<User, UUID>{
@Query("SELECT u FROM User u WHERE u.name = ?1 AND u.lastname1 = ?2")
List<User> findByNameAndLastName(String name, String lastName1);

@Query("SELECT u FROM User u WHERE u.role.name = ?1 AND u.deletedAt IS NULL")
    List<User> findAllByRoleName(String roleName);
}
