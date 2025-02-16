package com.estf.edoctorat.mappers;

import com.estf.edoctorat.dto.ProfesseurDto;
import com.estf.edoctorat.dto.SujetDto;
import com.estf.edoctorat.models.SujetModel;

public class SujetDtoMapper {
    public static SujetDto toDto(SujetModel sujet) {
        return new SujetDto(
                sujet.getId(),
                sujet.getProfesseur() != null ? ProfesseurDtoMapper.toDto(sujet.getProfesseur()) : null,
                sujet.getFormationDoctorale() != null ? FormationdoctoraleDtoMapper.toDto(sujet.getFormationDoctorale()) : null,
                sujet.getTitre(),
                sujet.getCodirecteur() != null ? ProfesseurDtoMapper.toDto(sujet.getCodirecteur()) : null,
                sujet.getDescription(),
                sujet.getPublier());
    }
}
