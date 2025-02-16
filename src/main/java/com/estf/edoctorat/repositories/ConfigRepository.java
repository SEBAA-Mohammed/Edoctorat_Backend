package com.estf.edoctorat.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estf.edoctorat.models.ConfigModel;

@Repository
public interface ConfigRepository extends JpaRepository<ConfigModel, Long> {
    Optional<ConfigModel> findFirstByOrderByIdDesc();
}