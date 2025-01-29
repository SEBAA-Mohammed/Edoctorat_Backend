package com.estf.edoctorat.repositories;

import com.estf.edoctorat.models.CommissionModel;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommissionRepository extends JpaRepository<CommissionModel, Long> {


    Page<CommissionModel> findByLaboratoire_Id(long labID, Pageable pageable);

    List<CommissionModel> findByLaboratoire_Id(long labID);

}
