package com.estf.edoctorat.repositories;

import com.estf.edoctorat.models.DiplomeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiplomeRepository extends JpaRepository<DiplomeModel, Long> {
}
