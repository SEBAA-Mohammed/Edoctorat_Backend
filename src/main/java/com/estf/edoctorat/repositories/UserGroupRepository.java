package com.estf.edoctorat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estf.edoctorat.models.UserGroupModel;

@Repository

public interface UserGroupRepository extends JpaRepository<UserGroupModel, Long> {
}