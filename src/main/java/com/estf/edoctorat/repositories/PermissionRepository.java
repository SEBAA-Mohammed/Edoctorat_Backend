package com.estf.edoctorat.repositories;


import com.estf.edoctorat.models.PermissionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionModel, Long> {
}
