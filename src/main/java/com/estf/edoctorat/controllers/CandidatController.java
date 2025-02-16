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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/")
@Tag(name = "Candidat", description = "Candidate management APIs")
@CrossOrigin(origins = "*")
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

    @Operation(summary = "Get all candidates", description = "Retrieves a list of all candidates")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved candidates"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping()
    public List<CandidatModel> index() {
        return candidatService.getCandidats();
    }

    @Operation(summary = "Get candidate by ID", description = "Retrieves a candidate by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved candidate"),
            @ApiResponse(responseCode = "404", description = "Candidate not found")
    })
    @GetMapping("/{id}/")
    public Optional<CandidatModel> getById(
            @Parameter(description = "ID of the candidate to retrieve") @PathVariable Long id) {
        return candidatService.getCandidatById(id);
    }

    @Operation(summary = "Search candidates by name", description = "Retrieves candidates matching the given name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved candidates"),
            @ApiResponse(responseCode = "404", description = "No candidates found")
    })
    @GetMapping("/search/{name}/")
    public List<CandidatModel> getByName(
            @Parameter(description = "Name to search for") @PathVariable String name) {
        return candidatService.getCandidatByName(name);
    }

    @Operation(summary = "Get candidate info", description = "Retrieves information for the currently authenticated candidate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved candidate info"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "404", description = "Candidate not found")
    })
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

    @Operation(summary = "Get candidate profile", description = "Retrieves detailed profile for a specific candidate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved candidate profile"),
            @ApiResponse(responseCode = "404", description = "Candidate not found")
    })
    @GetMapping("/get-candidat-profile/{id}/")
    public ResponseEntity<CandidatModel> getCandidatProfile(
            @Parameter(description = "ID of the candidate") @PathVariable Long id) {
        return ResponseEntity.ok(candidatService.getCandidatProfile(id));
    }

    @Operation(summary = "Create new candidate", description = "Creates a new candidate profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Candidate created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping
    public ResponseEntity<CandidatModel> create(
            @Parameter(description = "Candidate details", required = true) @RequestBody CandidatModel candidat) {
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

    @Operation(summary = "Update candidate", description = "Updates an existing candidate's information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidate updated successfully"),
            @ApiResponse(responseCode = "404", description = "Candidate not found")
    })
    @PutMapping("/{id}/")
    public CandidatModel update(
            @Parameter(description = "ID of the candidate to update") @PathVariable Long id,
            @Parameter(description = "Updated candidate details", required = true) @RequestBody CandidatModel candidat) {
        return candidatService.updateCandidat(id, candidat);
    }

    @Operation(summary = "Delete candidate", description = "Deletes a candidate profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidate deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Candidate not found")
    })
    @DeleteMapping("/{id}/")
    public String deleteCandidat(
            @Parameter(description = "ID of the candidate to delete") @PathVariable Long id) {
        candidatService.deleteCandidat(id);
        return "Candidat with ID " + id + " has been deleted!";
    }

    @Operation(summary = "Update candidate info with files", description = "Updates candidate information including photo upload")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidate info updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "404", description = "Candidate not found")
    })
    @PutMapping(value = "/candidat-info/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateCandidatInfo(
            @Parameter(description = "First name") @RequestPart(value = "prenom") String prenom,
            @Parameter(description = "Last name") @RequestPart(value = "nom") String nom,
            @Parameter(description = "Arabic first name") @RequestPart(value = "nomCandidatAr") String nomCandidatAr,
            @Parameter(description = "Arabic last name") @RequestPart(value = "prenomCandidatAr") String prenomCandidatAr,
            @Parameter(description = "CNE number") @RequestPart(value = "cne") String cne,
            @Parameter(description = "Address") @RequestPart(value = "adresse") String adresse,
            @Parameter(description = "Country") @RequestPart(value = "pays") String pays,
            @Parameter(description = "Gender") @RequestPart(value = "sexe") String sexe,
            @Parameter(description = "Birth city") @RequestPart(value = "villeDeNaissance") String villeDeNaissance,
            @Parameter(description = "Birth city in Arabic") @RequestPart(value = "villeDeNaissanceAr") String villeDeNaissanceAr,
            @Parameter(description = "Current city") @RequestPart(value = "ville") String ville,
            @Parameter(description = "Birth date") @RequestPart(value = "dateDeNaissance") String dateDeNaissance,
            @Parameter(description = "Email") @RequestPart(value = "mailCandidat") String mailCandidat,
            @Parameter(description = "Phone number") @RequestPart(value = "telCandidat") String telCandidat,
            @Parameter(description = "Profile photo") @RequestPart(value = "pathPhoto", required = false) MultipartFile pathPhoto,
            @Parameter(description = "Marital status") @RequestPart(value = "situation_familiale") String situation_familiale,
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