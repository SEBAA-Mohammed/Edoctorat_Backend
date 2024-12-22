package com.estf.edoctorat.controllers;

import com.estf.edoctorat.models.AnnexeModel;
import com.estf.edoctorat.services.AnnexeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/annexes")
public class AnnexeController {
    @Autowired
    private AnnexeService annexeService;

    @GetMapping
    public List<AnnexeModel> index(){
        return annexeService.getAllAnnexes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnnexeModel> getAnnexe(@PathVariable long id){
        Optional<AnnexeModel> annexeModel = annexeService.getAnnexeById(id);
        return annexeModel.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AnnexeModel> create(@RequestBody AnnexeModel annexeModel){
        AnnexeModel annexeModel1 =  annexeService.createAnnexe(annexeModel);
        if(annexeModel1 == null){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(annexeModel1);
    }

    @PutMapping("/")
    public AnnexeModel update(@PathVariable long id, @RequestBody AnnexeModel annexeModel){
        return annexeService.updateAnnexe(id, annexeModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        annexeService.deleteAnnexe(id);
        return ResponseEntity.noContent().build();
    }

}
