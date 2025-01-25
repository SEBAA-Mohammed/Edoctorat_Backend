package com.estf.edoctorat.controllers;

import com.estf.edoctorat.models.GroupPermissionModel;
import com.estf.edoctorat.services.GroupPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/group_permissions")
public class GroupPermissionController {

    @Autowired
    private GroupPermissionService groupPermissionService;

    @PostMapping
    public ResponseEntity<GroupPermissionModel> createGroupPermission(@RequestBody GroupPermissionModel groupPermission) {
        GroupPermissionModel created = groupPermissionService.createGroupPermission(groupPermission);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<GroupPermissionModel>> getAllGroupPermissions() {
        List<GroupPermissionModel> groupPermissions = groupPermissionService.getAllGroupPermissions();
        return new ResponseEntity<>(groupPermissions, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroupPermission(@PathVariable Long id) {
        try {
            groupPermissionService.deleteGroupPermission(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
