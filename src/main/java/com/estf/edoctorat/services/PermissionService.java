package com.estf.edoctorat.services;


import com.estf.edoctorat.models.PermissionModel;
import com.estf.edoctorat.repositories.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    public PermissionModel createAuthPermission(PermissionModel authPermission) {
        return permissionRepository.save(authPermission);
    }

    public List<PermissionModel> getAllAuthPermissions() {
        return permissionRepository.findAll();
    }

    public Optional<PermissionModel> getAuthPermissionById(Long id) {
        return permissionRepository.findById(id);
    }

    public PermissionModel updateAuthPermission(Long id, PermissionModel updatedAuthPermission) {
        return permissionRepository.findById(id)
                .map(authPermission -> {
                    authPermission.setName(updatedAuthPermission.getName());
                    authPermission.setCodename(updatedAuthPermission.getCodename());
                    return permissionRepository.save(authPermission);
                }).orElseThrow(() -> new RuntimeException("Permission not found with id " + id));
    }

    public void deleteAuthPermission(Long id) {
        permissionRepository.deleteById(id);
    }
}
