package com.estf.edoctorat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estf.edoctorat.models.ProfesseurLaboratoirModel;

@Repository
public interface ProfesseurLaboratoirRepository extends JpaRepository<ProfesseurLaboratoirModel, Long> {
}
