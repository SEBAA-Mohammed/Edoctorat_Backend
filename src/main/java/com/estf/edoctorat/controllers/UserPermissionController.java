package com.estf.edoctorat.controllers;

import com.estf.edoctorat.models.UserPermissionModel;
import com.estf.edoctorat.services.UserPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user_permissions")
public class UserPermissionController {

    @Autowired
    private UserPermissionService userPermissionService;

    @PostMapping
    public ResponseEntity<UserPermissionModel> createUserPermission(@RequestBody UserPermissionModel userPermission) {
        UserPermissionModel created = userPermissionService.createUserPermission(userPermission);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserPermissionModel>> getAllUserPermissions() {
        List<UserPermissionModel> userPermissions = userPermissionService.getAllUserPermissions();
        return new ResponseEntity<>(userPermissions, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserPermission(@PathVariable Long id) {
        try {
            userPermissionService.deleteUserPermission(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
