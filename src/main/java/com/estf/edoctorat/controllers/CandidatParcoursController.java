package com.estf.edoctorat.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.estf.edoctorat.config.CustomUserDetails;
import com.estf.edoctorat.dto.DiplomeDto;
import com.estf.edoctorat.mappers.DiplomeDtoMapper;
import com.estf.edoctorat.models.AnnexeModel;
import com.estf.edoctorat.models.DiplomeModel;
import com.estf.edoctorat.models.UserModel;
import com.estf.edoctorat.services.DiplomeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.CrossOrigin;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@Tag(name = "Candidate Academic Path", description = "APIs for managing candidate's diplomas and academic records")
@CrossOrigin(origins = "*")
public class CandidatParcoursController {

    @Autowired
    private DiplomeService diplomeService;

    @Operation(summary = "Get candidate diplomas", description = "Retrieves a paginated list of diplomas for the authenticated candidate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved diplomas", content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/candidat-parcours/")
    public ResponseEntity<Map<String, Object>> getDiplomes(
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "50") int limit,
            @Parameter(description = "Page offset") @RequestParam(defaultValue = "0") int offset,
            HttpServletRequest request) {

        UserDetails userDetails = (UserDetails) request.getAttribute("user");
        UserModel user = ((CustomUserDetails) userDetails).getUser();

        Page<DiplomeDto> diplomes = diplomeService.getDiplomesByCandidat(
                user.getCandidat().getId(),
                limit,
                offset);

        Map<String, Object> response = new HashMap<>();
        response.put("count", diplomes.getTotalElements());
        response.put("next", diplomes.hasNext() ? offset + limit : null);
        response.put("previous", offset > 0 ? Math.max(0, offset - limit) : null);
        response.put("results", diplomes.getContent());

        return ResponseEntity.ok(response);
    }

    @Value("${app.base-url}")
    private String baseUrl;

    private String getFullPdfUrl(String pathPdf) {
        if (pathPdf == null || pathPdf.isEmpty()) {
            return null;
        }
        return baseUrl + "/" + pathPdf;
    }

    @Operation(summary = "Add new diploma", description = "Creates a new diploma entry for the authenticated candidate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Diploma added successfully", content = @Content(schema = @Schema(implementation = DiplomeDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/candidat-parcours/")
    public ResponseEntity<DiplomeDto> addDiplome(
            @Parameter(description = "Diploma details", required = true) @RequestBody Map<String, Object> request,
            HttpServletRequest httpRequest) {

        try {
            UserDetails userDetails = (UserDetails) httpRequest.getAttribute("user");
            UserModel user = ((CustomUserDetails) userDetails).getUser();

            DiplomeModel diplome = new DiplomeModel();
            diplome.setAnnexes(new ArrayList<>());

            diplome.setIntitule((String) request.get("intitule"));
            diplome.setType((String) request.get("type"));
            diplome.setDateCommission(new SimpleDateFormat("yyyy-MM-dd").parse((String) request.get("dateCommission")));
            diplome.setPays((String) request.get("pays"));
            diplome.setVille((String) request.get("ville"));
            diplome.setProvince((String) request.get("province"));
            diplome.setMention((String) request.get("mention"));
            diplome.setEtablissement((String) request.get("etablissement"));
            diplome.setSpecialite((String) request.get("specialite"));
            diplome.setMoyen_generale(Double.parseDouble(request.get("moyen_generale").toString()));
            diplome.setCandidat(user.getCandidat());

            String diplomePath = (String) request.get("diplomePath");
            if (diplomePath != null) {
                AnnexeModel annexe = new AnnexeModel();
                annexe.setTypeAnnexe("diplome");
                annexe.setTitre(diplomePath.substring(diplomePath.lastIndexOf('/') + 1));
                annexe.setPathFile(diplomePath);
                annexe.setDiplome(diplome);
                diplome.getAnnexes().add(annexe);
            }

            String relevePath = (String) request.get("relevePath");
            if (relevePath != null) {
                AnnexeModel annexe = new AnnexeModel();
                annexe.setTypeAnnexe("releve");
                annexe.setTitre(relevePath.substring(relevePath.lastIndexOf('/') + 1));
                annexe.setPathFile(relevePath);
                annexe.setDiplome(diplome);
                diplome.getAnnexes().add(annexe);
            }

            DiplomeModel saved = diplomeService.create(diplome);
            DiplomeDto dto = DiplomeDtoMapper.toDto(saved);

            return ResponseEntity.ok(dto);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Update diploma", description = "Updates an existing diploma entry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Diploma updated successfully", content = @Content(schema = @Schema(implementation = DiplomeDto.class))),
            @ApiResponse(responseCode = "404", description = "Diploma not found"),
            @ApiResponse(responseCode = "401", description = "Not authenticated")
    })
    @PatchMapping("/candidat-parcours/{id}/")
    public ResponseEntity<DiplomeDto> updateDiplome(
            @Parameter(description = "ID of the diploma to update") @PathVariable Long id,
            @Parameter(description = "Updated diploma details", required = true) @RequestBody DiplomeModel updates) {

        DiplomeModel updated = diplomeService.update(id, updates);
        return ResponseEntity.ok(DiplomeDtoMapper.toDto(updated));
    }

    @Operation(summary = "Delete diploma", description = "Deletes a diploma entry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Diploma deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Diploma not found"),
            @ApiResponse(responseCode = "401", description = "Not authenticated")
    })
    @DeleteMapping("/candidat-parcours/{id}/")
    public ResponseEntity<Void> deleteDiplome(
            @Parameter(description = "ID of the diploma to delete") @PathVariable Long id) {
        diplomeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Upload diploma files", description = "Uploads diploma and transcript files for a candidate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Files uploaded successfully", content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "400", description = "Invalid file format"),
            @ApiResponse(responseCode = "500", description = "Upload failed")
    })
    @PostMapping(value = "/upload-files/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> uploadFiles(
            @Parameter(description = "Diploma PDF file") @RequestPart(value = "diplomeFile", required = false) MultipartFile diplomeFile,
            @Parameter(description = "Transcript PDF file") @RequestPart(value = "releveFile", required = false) MultipartFile releveFile) {

        Map<String, String> response = new HashMap<>();
        String uploadDir = "uploads/diplomes/";
        Path uploadPath = Paths.get(uploadDir);

        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            if (diplomeFile != null && !diplomeFile.isEmpty()) {
                // if (!diplomeFile.getContentType().equals("application/pdf")) {
                // return ResponseEntity.badRequest()
                // .body(Collections.singletonMap("error", "Only PDF files are allowed"));
                // }
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
                String filename = timestamp + "_" + diplomeFile.getOriginalFilename();
                Path filePath = uploadPath.resolve(filename);
                Files.copy(diplomeFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                response.put("diplomePath", uploadDir + filename);
            }

            if (releveFile != null && !releveFile.isEmpty()) {
                // if (!releveFile.getContentType().equals("application/pdf")) {
                // return ResponseEntity.badRequest()
                // .body(Collections.singletonMap("error", "Only PDF files are allowed"));
                // }
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
                String filename = timestamp + "_" + releveFile.getOriginalFilename();
                Path filePath = uploadPath.resolve(filename);
                Files.copy(releveFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                response.put("relevePath", uploadDir + filename);
            }

            return ResponseEntity.ok(response);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "File upload failed"));
        }
    }
}
