package com.estf.edoctorat.controllers;

import com.estf.edoctorat.config.CustomUserDetails;
import com.estf.edoctorat.dto.PostulerDto;
import com.estf.edoctorat.mappers.PostulerDtoMapper;
import com.estf.edoctorat.mappers.SujetDtoMapper;
import com.estf.edoctorat.models.CandidatPostulerModel;
import com.estf.edoctorat.models.SujetModel;
import com.estf.edoctorat.models.UserModel;
import com.estf.edoctorat.services.CandidatPostulerService;
import com.estf.edoctorat.services.SujetService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CandidatPostulesController {

        @Autowired
        private CandidatPostulerService candidatPostulerService;

        @Autowired
        private SujetService sujetService;

        @GetMapping("/candidat-postules/")
        public ResponseEntity<Map<String, Object>> getSelectedSubjects(
                        HttpServletRequest request,
                        @RequestParam(defaultValue = "50") int limit,
                        @RequestParam(defaultValue = "0") int offset) {

                UserDetails userDetails = (UserDetails) request.getAttribute("user");
                UserModel user = ((CustomUserDetails) userDetails).getUser();

                Page<CandidatPostulerModel> postules = candidatPostulerService.getByCandidatId(
                                user.getCandidat().getId(),
                                PageRequest.of(offset / limit, limit));

                Map<String, Object> response = new HashMap<>();
                response.put("count", postules.getTotalElements());
                response.put("next", postules.hasNext() ? offset + limit : null);
                response.put("previous", offset > 0 ? Math.max(0, offset - limit) : null);
                response.put("results", postules.getContent().stream()
                                .map(PostulerDtoMapper::toDto)
                                .collect(Collectors.toList()));

                return ResponseEntity.ok(response);
        }

        @PostMapping("/candidat-postules/")
        public ResponseEntity<PostulerDto> postuler(
                        @RequestBody Map<String, Long> request,
                        HttpServletRequest httpRequest) {

                UserDetails userDetails = (UserDetails) httpRequest.getAttribute("user");
                UserModel user = ((CustomUserDetails) userDetails).getUser();

                SujetModel sujet = sujetService.getById(request.get("sujet"))
                                .orElseThrow(() -> new RuntimeException("Sujet not found"));

                CandidatPostulerModel postulation = new CandidatPostulerModel();
                postulation.setSujet(sujet);
                postulation.setCandidat(user.getCandidat());

                CandidatPostulerModel saved = candidatPostulerService.create(postulation);
                return ResponseEntity.ok(PostulerDtoMapper.toDto(saved));
        }

        @DeleteMapping("/candidat-postules/{id}/")
        public ResponseEntity<Void> deletePostule(@PathVariable Long id) {
                candidatPostulerService.delete(id);
                return ResponseEntity.noContent().build();
        }

        @PatchMapping("/candidat-postules/{id}/")
        public ResponseEntity<PostulerDto> updatePostuler(
                        @PathVariable Long id,
                        @RequestBody Map<String, String> pathThese) {

                CandidatPostulerModel postulation = candidatPostulerService.getById(id)
                                .orElseThrow(() -> new RuntimeException("Postulation not found"));

                postulation.setPathFile(pathThese.get("pathFile"));

                CandidatPostulerModel updated = candidatPostulerService.update(id, postulation);
                return ResponseEntity.ok(PostulerDtoMapper.toDto(updated));
        }

        @GetMapping("/get-published-subjects/")
        public ResponseEntity<Map<String, Object>> getPublishedSubjects(
                        @RequestParam(defaultValue = "50") int limit,
                        @RequestParam(defaultValue = "0") int offset) {

                Page<SujetModel> sujets = sujetService.getPublishedSubjects(limit, offset);

                Map<String, Object> response = new HashMap<>();
                response.put("count", sujets.getTotalElements());
                response.put("next", sujets.hasNext() ? offset + limit : null);
                response.put("previous", offset > 0 ? Math.max(0, offset - limit) : null);
                response.put("results", sujets.getContent().stream()
                                .map(SujetDtoMapper::toDto)
                                .collect(Collectors.toList()));

                return ResponseEntity.ok(response);
        }

}