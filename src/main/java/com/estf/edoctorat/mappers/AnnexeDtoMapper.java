package com.estf.edoctorat.mappers;

import com.estf.edoctorat.dto.AnnexeDto;
import com.estf.edoctorat.models.AnnexeModel;

public class AnnexeDtoMapper {
    
    public static AnnexeDto toDto(AnnexeModel annexe) {
        return new AnnexeDto(
            annexe.getTypeAnnexe(),
            annexe.getTitre(),
            annexe.getPathFile()
        );
    }
    
    public static AnnexeModel toEntity(AnnexeDto dto) {
        AnnexeModel annexe = new AnnexeModel();
        annexe.setTypeAnnexe(dto.getTypeAnnexe());
        annexe.setTitre(dto.getTitre());
        annexe.setPathFile(dto.getPathFile());
        return annexe;
    }
}
