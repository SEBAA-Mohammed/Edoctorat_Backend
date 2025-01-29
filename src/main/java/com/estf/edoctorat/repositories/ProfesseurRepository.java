package com.estf.edoctorat.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.estf.edoctorat.models.ProfesseurModel;
import com.estf.edoctorat.models.UserModel;

@Repository
public interface ProfesseurRepository extends JpaRepository<ProfesseurModel, Long> {
    Optional<ProfesseurModel> findByUser(UserModel user);

    @Query("SELECT p FROM ProfesseurModel p WHERE p.user.email = :email")
    Optional<ProfesseurModel> findByUserEmail(@Param("email") String email);

    @Query("SELECT p FROM ProfesseurModel p WHERE p.labo_id = :labID")
    Page<ProfesseurModel> findByLabo_id(@Param("labID") Long labID, Pageable pageable);

    @Query("select P from ProfesseurModel P where P.labo_id = :labID")
    List<ProfesseurModel> findByLabo_id(@Param("labID") Long labID);

}