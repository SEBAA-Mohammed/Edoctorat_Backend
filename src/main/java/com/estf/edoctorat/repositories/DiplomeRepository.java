package com.estf.edoctorat.repositories;

import com.estf.edoctorat.models.DiplomeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface DiplomeRepository extends JpaRepository<DiplomeModel, Long> {
    
    @Query("SELECT d FROM DiplomeModel d WHERE d.candidat.id = :candidatId")
    Page<DiplomeModel> findByCandidatId(@Param("candidatId") Long candidatId, Pageable pageable);
}
