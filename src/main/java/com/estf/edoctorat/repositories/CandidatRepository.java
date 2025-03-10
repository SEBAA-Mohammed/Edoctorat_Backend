package com.estf.edoctorat.repositories;

import com.estf.edoctorat.models.CandidatModel;
import com.estf.edoctorat.models.UserModel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidatRepository extends JpaRepository<CandidatModel, Long> {
    List<CandidatModel> findByNomCandidatAr(String name);

    Optional<CandidatModel> findByUser(UserModel user);
    @Query("SELECT c FROM CandidatModel c JOIN c.candidatPostulers cp JOIN cp.sujet s JOIN s.formationDoctorale f JOIN f.ced ced WHERE ced.id = :cedId")
    Page<CandidatModel> findByCedId(@Param("cedId") Long cedId, Pageable pageable);
}
