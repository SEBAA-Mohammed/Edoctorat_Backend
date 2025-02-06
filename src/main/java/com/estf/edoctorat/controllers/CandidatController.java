package com.estf.edoctorat.controllers;

import com.estf.edoctorat.dto.CandidatDto;
import com.estf.edoctorat.models.CandidatModel;
import com.estf.edoctorat.models.UserModel;
import com.estf.edoctorat.repositories.UserRepository;
import com.estf.edoctorat.services.CandidatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/")
public class CandidatController {

    @Autowired
    private CandidatService candidatService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public List<CandidatModel> index() {
        return candidatService.getCandidats();
    }

    @GetMapping("/{id}")
    public Optional<CandidatModel> getById(@PathVariable Long id) {
        return candidatService.getCandidatById(id);
    }

    @GetMapping("/search/{name}")
    public List<CandidatModel> getByName(@PathVariable String name) {
        return candidatService.getCandidatByName(name);
    }

    @GetMapping("/candidat-info/")
    public ResponseEntity<CandidatDto> getCandidatInfo(@AuthenticationPrincipal UserDetails userDetails) {
        UserModel user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        CandidatDto candidat = candidatService.getCandidatInfo(user);
        return ResponseEntity.ok(candidat);
    }

    @GetMapping("/get-candidat-profile/{id}/")
    public ResponseEntity<CandidatModel> getCandidatProfile(@PathVariable Long id) {
        CandidatModel candidat = candidatService.getCandidatProfile(id);
        return ResponseEntity.ok(candidat);
    }

    @PostMapping
    public ResponseEntity<CandidatModel> create(@RequestBody CandidatModel candidat) {
        if (candidat.getUser() == null || candidat.getUser().getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<UserModel> user = userRepository.findById(candidat.getUser().getId());
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        candidat.setUser(user.get());
        CandidatModel createdCandidat = candidatService.createCandidat(candidat);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdCandidat);
    }

    @PutMapping("/{id}")
    public CandidatModel update(@PathVariable Long id, @RequestBody CandidatModel candidat) {
        return candidatService.updateCandidat(id, candidat);
    }

    @DeleteMapping("/{id}")
    public String deleteCandidat(@PathVariable Long id) {
        candidatService.deleteCandidat(id);
        return "Candidat with ID " + id + " has been deleted!";
    }

}
