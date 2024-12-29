package com.estf.edoctorat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estf.edoctorat.models.ProfesseurModel;

@Repository
public interface ProfesseurRepository extends JpaRepository<ProfesseurModel, Long> {

    
}