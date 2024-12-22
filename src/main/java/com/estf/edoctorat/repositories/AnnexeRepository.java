package com.estf.edoctorat.repositories;

import com.estf.edoctorat.models.AnnexeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnexeRepository extends JpaRepository<AnnexeModel, Long> {
}
