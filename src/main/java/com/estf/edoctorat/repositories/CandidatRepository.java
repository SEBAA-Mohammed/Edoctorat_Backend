package com.estf.edoctorat.repositories;

import com.estf.edoctorat.models.CandidatModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidatRepository extends JpaRepository<CandidatModel, Long> {
    List<CandidatModel> findByNomCandidatAr(String name);
}
