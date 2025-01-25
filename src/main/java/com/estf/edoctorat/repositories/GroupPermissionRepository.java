package com.estf.edoctorat.repositories;

import com.estf.edoctorat.models.GroupPermissionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupPermissionRepository extends JpaRepository<GroupPermissionModel, Long> {
}
