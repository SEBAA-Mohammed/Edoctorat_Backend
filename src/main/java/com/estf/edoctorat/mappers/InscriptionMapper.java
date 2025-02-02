package com.estf.edoctorat.mappers;

import com.estf.edoctorat.dto.InscriptionDto;
import com.estf.edoctorat.models.InscriptionModel;
import com.estf.edoctorat.models.CandidatPostulerModel;
import com.estf.edoctorat.repositories.CandidatPostulerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InscriptionMapper {

    private final CandidatPostulerRepository candidatPostulerRepository;

    public InscriptionDto toDto(InscriptionModel inscription) {
        if (inscription == null) {
            return null;
        }

        // Get pathFile from CandidatPostuler
        String pathFile = candidatPostulerRepository.findByCandidatAndSujet(
                        inscription.getCandidat(),
                        inscription.getSujet())
                .map(CandidatPostulerModel::getPathFile)
                .orElse(null);

        return new InscriptionDto(
                inscription.getId(),
                CandidatDtoMapper.toDto(inscription.getCandidat()),
                SujetMapper.toDto(inscription.getSujet()),
                inscription.getDateDisposeDossier() != null ?
                        inscription.getDateDisposeDossier().toString() : null,
                inscription.getRemarque(),
                (inscription.getValider() != 0),
                pathFile
        );
    }

    public InscriptionModel toEntity(InscriptionDto dto) {
        if (dto == null) {
            return null;
        }

        InscriptionModel inscription = new InscriptionModel();
        inscription.setId(dto.getId());
        inscription.setCandidat(CandidatDtoMapper.toEntity(dto.getCandidat()));
        inscription.setSujet(SujetMapper.toEntity(dto.getSujet()));
        // Handle date conversion if needed
        inscription.setRemarque(dto.getRemarque());
        inscription.setValider((byte) (dto.isValider() ? 1 : 0));

        return inscription;
    }
}