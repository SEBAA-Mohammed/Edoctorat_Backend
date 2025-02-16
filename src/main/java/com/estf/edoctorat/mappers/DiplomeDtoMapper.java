package com.estf.edoctorat.mappers;

import java.util.stream.Collectors;

import com.estf.edoctorat.dto.DiplomeDto;
import com.estf.edoctorat.models.DiplomeModel;

public class DiplomeDtoMapper {
    
    public static DiplomeDto toDto(DiplomeModel diplome) {
        return new DiplomeDto(
            diplome.getId(),
            diplome.getIntitule(),
            diplome.getType(),
            diplome.getDateCommission().toString(),
            diplome.getMention(),
            diplome.getPays(),
            diplome.getEtablissement(),
            diplome.getSpecialite(),
            diplome.getVille(),
            diplome.getProvince(),
            diplome.getMoyen_generale(),
            diplome.getAnnexes().stream()
                .map(AnnexeDtoMapper::toDto)
                .collect(Collectors.toList())
        );
    }
}
