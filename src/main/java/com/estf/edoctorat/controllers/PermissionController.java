package com.estf.edoctorat.controllers;

import com.estf.edoctorat.models.PermissionModel;
import com.estf.edoctorat.services.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth_permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @PostMapping
    public ResponseEntity<PermissionModel> createAuthPermission(@RequestBody PermissionModel authPermission) {
        PermissionModel createdAuthPermission = permissionService.createAuthPermission(authPermission);
        return new ResponseEntity<>(createdAuthPermission, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PermissionModel>> getAllAuthPermissions() {
        List<PermissionModel> authPermissions = permissionService.getAllAuthPermissions();
        return new ResponseEntity<>(authPermissions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermissionModel> getAuthPermissionById(@PathVariable Long id) {
        Optional<PermissionModel> authPermission = permissionService.getAuthPermissionById(id);
        return authPermission.map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PermissionModel> updateAuthPermission(@PathVariable Long id, @RequestBody PermissionModel updatedAuthPermission) {
        try {
            PermissionModel updated = permissionService.updateAuthPermission(id, updatedAuthPermission);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthPermission(@PathVariable Long id) {
        try {
            permissionService.deleteAuthPermission(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}