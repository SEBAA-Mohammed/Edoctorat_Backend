package com.estf.edoctorat.controllers;

import com.estf.edoctorat.config.CustomUserDetails;
import com.estf.edoctorat.dto.CandidatDto;
import com.estf.edoctorat.mappers.CandidatDtoMapper;
import com.estf.edoctorat.models.CandidatModel;
import com.estf.edoctorat.models.PaysModel;
import com.estf.edoctorat.models.UserModel;
import com.estf.edoctorat.repositories.PaysRepository;
import com.estf.edoctorat.repositories.UserRepository;
import com.estf.edoctorat.services.CandidatService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/")
public class CandidatController {

    @Value("${app.base-url}")
    private String baseUrl;

    @Autowired
    private CandidatService candidatService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaysRepository paysRepository;

    private String getFullPhotoUrl(String pathPhoto) {
        if (pathPhoto == null || pathPhoto.isEmpty()) {
            return null;
        }
        return baseUrl + "/" + pathPhoto;
    }

    @GetMapping()
    public List<CandidatModel> index() {
        return candidatService.getCandidats();
    }

    @GetMapping("/{id}/")
    public Optional<CandidatModel> getById(@PathVariable Long id) {
        return candidatService.getCandidatById(id);
    }

    @GetMapping("/search/{name}/")
    public List<CandidatModel> getByName(@PathVariable String name) {
        return candidatService.getCandidatByName(name);
    }

    @GetMapping("/candidat-info/")
    public ResponseEntity<CandidatDto> getCandidatInfo(@AuthenticationPrincipal UserDetails userDetails) {
        UserModel user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        CandidatDto candidat = candidatService.getCandidatInfo(user);

        if (candidat != null) {
            candidat.setPathPhoto(getFullPhotoUrl(candidat.getPathPhoto()));
        }

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

    @PutMapping("/{id}/")
    public CandidatModel update(@PathVariable Long id, @RequestBody CandidatModel candidat) {
        return candidatService.updateCandidat(id, candidat);
    }

    @DeleteMapping("/{id}/")
    public String deleteCandidat(@PathVariable Long id) {
        candidatService.deleteCandidat(id);
        return "Candidat with ID " + id + " has been deleted!";
    }

    @PutMapping(value = "/candidat-info/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateCandidatInfo(
            @RequestPart(value = "prenom") String prenom,
            @RequestPart(value = "nom") String nom,
            @RequestPart(value = "nomCandidatAr") String nomCandidatAr,
            @RequestPart(value = "prenomCandidatAr") String prenomCandidatAr,
            @RequestPart(value = "cne") String cne,
            @RequestPart(value = "adresse") String adresse,
            @RequestPart(value = "pays") String pays,
            @RequestPart(value = "sexe") String sexe,
            @RequestPart(value = "villeDeNaissance") String villeDeNaissance,
            @RequestPart(value = "villeDeNaissanceAr") String villeDeNaissanceAr,
            @RequestPart(value = "ville") String ville,
            @RequestPart(value = "dateDeNaissance") String dateDeNaissance,
            @RequestPart(value = "mailCandidat") String mailCandidat,
            @RequestPart(value = "telCandidat") String telCandidat,
            @RequestPart(value = "pathPhoto", required = false) MultipartFile pathPhoto,
            @RequestPart(value = "situation_familiale") String situation_familiale,
            HttpServletRequest request) {

        try {
            UserDetails userDetails = (UserDetails) request.getAttribute("user");
            if (userDetails == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
            }

            UserModel user = ((CustomUserDetails) userDetails).getUser();
            CandidatModel candidat = user.getCandidat();

            if (candidat == null) {
                return ResponseEntity.notFound().build();
            }

            // Update user info
            user.setFirst_name(prenom);
            user.setLast_name(nom);
            user.setEmail(mailCandidat);
            userRepository.save(user);

            // Update pays
            PaysModel paysModel = paysRepository.findByNom(pays)
                    .orElseGet(() -> {
                        PaysModel newPays = new PaysModel();
                        newPays.setNom(pays);
                        return paysRepository.save(newPays);
                    });

            // Update candidat info
            candidat.setNomCandidatAr(nomCandidatAr);
            candidat.setPrenomCandidatAr(prenomCandidatAr);
            candidat.setCne(cne);
            candidat.setAdresse(adresse);
            candidat.setPays(paysModel);
            candidat.setSexe(sexe);
            candidat.setVilleDeNaissance(villeDeNaissance);
            candidat.setVilleDeNaissanceAr(villeDeNaissanceAr);
            candidat.setVille(ville);
            if (dateDeNaissance != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                candidat.setDateDeNaissance(dateFormat.parse(dateDeNaissance));
            }
            candidat.setTelCandidat(telCandidat);
            candidat.setSituation_familiale(situation_familiale);

            // Handle photo upload
            if (pathPhoto != null && !pathPhoto.isEmpty()) {
                String fileName = UUID.randomUUID().toString() + "_" + pathPhoto.getOriginalFilename();
                String uploadDir = "uploads/photos/";
                Path uploadPath = Paths.get(uploadDir);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                Path filePath = uploadPath.resolve(fileName);
                Files.copy(pathPhoto.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                candidat.setPathPhoto(uploadDir + fileName);
            }

            CandidatModel updatedCandidat = candidatService.updateCandidat(candidat.getId(), candidat);
            CandidatDto dto = CandidatDtoMapper.toDto(updatedCandidat);
            dto.setPathPhoto(getFullPhotoUrl(dto.getPathPhoto()));
            return ResponseEntity.ok(dto);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating candidat: " + e.getMessage());
        }
    }

}