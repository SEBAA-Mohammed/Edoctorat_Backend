package com.estf.edoctorat.services;

import com.estf.edoctorat.dto.PostulerDto;
import com.estf.edoctorat.mappers.PostulerDtoMapper;
import com.estf.edoctorat.models.CandidatPostulerModel;
import com.estf.edoctorat.repositories.CandidatPostulerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ScolariteService {

    private final CandidatPostulerRepository candidatPostulerRepository;

    public Page<PostulerDto> getScolariteCandidats(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return candidatPostulerRepository.findAll(pageRequest)
                .map(PostulerDtoMapper::toDto);
    }

    public ResponseEntity<PostulerDto> updateScolarite(Long id, Map<String, Object> updates) {
        Optional<CandidatPostulerModel> postulerOptional = candidatPostulerRepository.findById(id);

        if (!postulerOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        CandidatPostulerModel postuler = postulerOptional.get();
        if (updates.containsKey("valider")) {
            postuler.getCandidat().setEtatDossier((Integer) updates.get("valider"));
        }

        CandidatPostulerModel updated = candidatPostulerRepository.save(postuler);
        return ResponseEntity.ok(PostulerDtoMapper.toDto(updated));
    }
}