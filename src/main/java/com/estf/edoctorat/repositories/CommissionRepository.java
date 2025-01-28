package com.estf.edoctorat.repositories;

import com.estf.edoctorat.models.CommissionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface CommissionRepository extends JpaRepository<CommissionModel, Long> {

    List<CommissionModel> findByLaboratoire_Id(long labID);

}
