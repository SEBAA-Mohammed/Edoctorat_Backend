package com.estf.edoctorat.repositories;


import com.estf.edoctorat.models.UserPermissionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPermissionRepository extends JpaRepository<UserPermissionModel, Long> {
}
