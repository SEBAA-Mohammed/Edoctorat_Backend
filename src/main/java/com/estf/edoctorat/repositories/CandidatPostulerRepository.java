package com.estf.edoctorat.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estf.edoctorat.models.CandidatPostulerModel;

@Repository


public interface CandidatPostulerRepository extends JpaRepository<CandidatPostulerModel, Long> {


}