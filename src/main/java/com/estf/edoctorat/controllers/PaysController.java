package com.estf.edoctorat.controllers;

import com.estf.edoctorat.models.PaysModel;
import com.estf.edoctorat.services.PaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pays")
public class PaysController {

    @Autowired
    private PaysService paysService;

    @PostMapping
    public ResponseEntity<PaysModel> createPays(@RequestBody PaysModel pays) {
        PaysModel createdPays = paysService.createPays(pays);
        return new ResponseEntity<>(createdPays, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PaysModel>> getAllPays() {
        List<PaysModel> paysList = paysService.getAllPays();
        return new ResponseEntity<>(paysList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaysModel> getPaysById(@PathVariable Long id) {
        Optional<PaysModel> pays = paysService.getPaysById(id);
        return pays.map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaysModel> updatePays(@PathVariable Long id, @RequestBody PaysModel updatedPays) {
        try {
            PaysModel updated = paysService.updatePays(id, updatedPays);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePays(@PathVariable Long id) {
        try {
            paysService.deletePays(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
