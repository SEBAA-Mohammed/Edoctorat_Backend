package com.estf.edoctorat.services;

import com.estf.edoctorat.models.UserPermissionModel;
import com.estf.edoctorat.repositories.UserPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPermissionService {

    @Autowired
    private UserPermissionRepository userPermissionRepository;

    public UserPermissionModel createUserPermission(UserPermissionModel userPermission) {
        return userPermissionRepository.save(userPermission);
    }

    public List<UserPermissionModel> getAllUserPermissions() {
        return userPermissionRepository.findAll();
    }

    public void deleteUserPermission(Long id) {
        userPermissionRepository.deleteById(id);
    }
}
