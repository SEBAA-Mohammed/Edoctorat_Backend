package com.estf.edoctorat.repositories;

import com.estf.edoctorat.models.PaysModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaysRepository extends JpaRepository<PaysModel,Long> {
}
