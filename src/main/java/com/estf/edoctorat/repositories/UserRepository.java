package com.estf.edoctorat.repositories;

import com.estf.edoctorat.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    @Query("SELECT u FROM UserModel u LEFT JOIN FETCH u.userGroup ug LEFT JOIN FETCH ug.groups WHERE u.email = :email")
    Optional<UserModel> findByEmail(String email);
    Optional<UserModel> findByUsername(String username);
    Boolean existsByEmail(String email);
}