package com.estf.edoctorat.repositories;

import com.estf.edoctorat.models.CommissionModel;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommissionRepository extends JpaRepository<CommissionModel, Long> {


    Page<CommissionModel> findByLaboratoire_Id(long labID, Pageable pageable);

    List<CommissionModel> findByLaboratoire_Id(long labID);

    @Query("SELECT c FROM CommissionModel c JOIN c.laboratoire l JOIN l.ced ced WHERE ced.id = :cedId")
    Page<CommissionModel> findByCedId(@Param("cedId") Long cedId, Pageable pageable);

    @Query("SELECT c FROM CommissionModel c JOIN c.commissionProfesseurs cp WHERE cp.professeur.id = :profId")
    List<CommissionModel> findCommissionsByProfesseurId(@Param("profId") Long profId);
}
