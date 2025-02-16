package com.estf.edoctorat.repositories;

import com.estf.edoctorat.models.CandidatModel;
import com.estf.edoctorat.models.SujetModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estf.edoctorat.models.CandidatPostulerModel;

import java.util.List;
import java.util.Optional;

@Repository

public interface CandidatPostulerRepository extends JpaRepository<CandidatPostulerModel, Long> {

    List<CandidatPostulerModel> findBySujetProfesseurId(Long professorId);

    Optional<CandidatPostulerModel> findByCandidatAndSujet(CandidatModel candidat, SujetModel sujet);

    Page<CandidatPostulerModel> findByCandidatId(Long candidatId, Pageable pageable);

}