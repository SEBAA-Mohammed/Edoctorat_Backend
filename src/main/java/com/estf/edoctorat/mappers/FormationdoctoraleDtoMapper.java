package com.estf.edoctorat.mappers;

import com.estf.edoctorat.dto.FormationdoctoraleDto;
import com.estf.edoctorat.models.FormationdoctoraleModel;

public class FormationdoctoraleDtoMapper {

    public static FormationdoctoraleDto toDto(FormationdoctoraleModel FD){
        return new FormationdoctoraleDto(
                FD.getId(),
                FD.getCed().getId(),
                FD.getEtablissement().getIdEtablissement(),
                FD.getAxeDeRecherche(),
                FD.getPathImage(),
                FD.getTitre(),
                FD.getInitiale(),
                FD.getDateAccreditation()
        );
    }

}
