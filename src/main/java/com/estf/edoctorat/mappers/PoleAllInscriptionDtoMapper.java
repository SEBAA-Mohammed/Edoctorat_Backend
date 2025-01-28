package com.estf.edoctorat.mappers;

import com.estf.edoctorat.dto.PoleAllInscriptionDto;
import com.estf.edoctorat.models.InscriptionModel;

import java.util.List;
import java.util.stream.Collectors;

public class PoleAllInscriptionDtoMapper {
    public static PoleAllInscriptionDto toDto(InscriptionModel inscription) {
        return new PoleAllInscriptionDto(
                inscription.getId(),
                inscription.getDateDisposeDossier(),
                inscription.getRemarque(),
                inscription.getValider() == 1,
                inscription.getCandidat(),
                inscription.getSujet()
        );
    }

    public static List<PoleAllInscriptionDto> toDtoList(List<InscriptionModel> inscriptions) {
        return inscriptions.stream()
                .map(PoleAllInscriptionDtoMapper::toDto)
                .collect(Collectors.toList());
    }
}