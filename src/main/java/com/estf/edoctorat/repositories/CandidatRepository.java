package com.estf.edoctorat.repositories;

import com.estf.edoctorat.models.CandidatModel;
import com.estf.edoctorat.models.UserModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidatRepository extends JpaRepository<CandidatModel, Long> {
    List<CandidatModel> findByNomCandidatAr(String name);

    Optional<CandidatModel> findByUser(UserModel user);
    List<CandidatModel> findByCandidatPostuler_Sujet_FormationDoctorale_Ced_Id(Long cedID);
}
