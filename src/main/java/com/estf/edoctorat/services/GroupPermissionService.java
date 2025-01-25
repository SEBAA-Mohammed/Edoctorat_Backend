package com.estf.edoctorat.services;

import com.estf.edoctorat.models.GroupPermissionModel;
import com.estf.edoctorat.repositories.GroupPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupPermissionService {

    @Autowired
    private GroupPermissionRepository groupPermissionRepository;

    public GroupPermissionModel createGroupPermission(GroupPermissionModel groupPermission) {
        return groupPermissionRepository.save(groupPermission);
    }

    public List<GroupPermissionModel> getAllGroupPermissions() {
        return groupPermissionRepository.findAll();
    }

    public void deleteGroupPermission(Long id) {
        groupPermissionRepository.deleteById(id);
    }
}
