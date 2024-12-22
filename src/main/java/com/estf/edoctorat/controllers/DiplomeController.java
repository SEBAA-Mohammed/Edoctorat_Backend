package com.estf.edoctorat.controllers;

import com.estf.edoctorat.models.DiplomeModel;
import com.estf.edoctorat.services.DiplomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/diplomes")
public class DiplomeController {
    @Autowired
    private DiplomeService diplomeService;

    @GetMapping
    public List<DiplomeModel> index() {
        return diplomeService.getAllDiplomes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiplomeModel> getDiplome(@PathVariable long id) {
      Optional<DiplomeModel> diplomeModel =  diplomeService.getDiplomeById(id);
      return diplomeModel.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DiplomeModel> create(@RequestBody DiplomeModel diplome) {
        DiplomeModel diplomeModel = diplomeService.createDiplome(diplome);

        if(diplomeModel == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(diplomeModel);
    }

    @PutMapping("/{id}")
    public DiplomeModel update(long id, DiplomeModel diplome) {
        return diplomeService.updateDiplomeById(id, diplome);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(long id) {
        diplomeService.deleteDiplomeById(id);
        return ResponseEntity.noContent().build();
    }
}
