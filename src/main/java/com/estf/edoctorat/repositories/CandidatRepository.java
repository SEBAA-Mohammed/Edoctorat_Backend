package com.estf.edoctorat.repositories;

import com.estf.edoctorat.models.CandidatModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidatRepository extends JpaRepository<CandidatModel, Long> {
}