package com.estf.edoctorat.repositories;

import com.estf.edoctorat.models.PermissionModel;
import com.estf.edoctorat.models.SujetModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SujetRepository extends JpaRepository<SujetModel, Long> {

    List<SujetModel> findSujetByProfesseur_Id(long profID);

}
