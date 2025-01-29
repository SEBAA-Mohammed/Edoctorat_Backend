package com.estf.edoctorat.repositories;

import com.estf.edoctorat.models.PermissionModel;
import com.estf.edoctorat.models.SujetModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SujetRepository extends JpaRepository<SujetModel, Long> {

    Page<SujetModel> findSujetByProfesseur_Id(long profID, Pageable pageable);

    List<SujetModel> findSujetByProfesseur_Id(long profID);

    @Query("SELECT s FROM SujetModel s JOIN s.professeur p JOIN p.ced ced WHERE ced.id = :cedId")
    Page<SujetModel> findByCedId(@Param("cedId") Long cedId, Pageable pageable);

}
