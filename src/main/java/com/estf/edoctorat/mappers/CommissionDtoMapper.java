package com.estf.edoctorat.mappers;


import com.estf.edoctorat.dto.CommissionCreationDto;
import com.estf.edoctorat.dto.CommissionDto;
import com.estf.edoctorat.dto.ProfesseurDto;
import com.estf.edoctorat.dto.SujetDto;
import com.estf.edoctorat.models.CommissionModel;
import com.estf.edoctorat.models.LaboratoireModel;
import com.estf.edoctorat.services.LaboratoireService;
import com.estf.edoctorat.services.SujetService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class CommissionDtoMapper {


    public static CommissionModel toCommission(CommissionCreationDto dto, LaboratoireService laboServ){

        LaboratoireModel labo = laboServ.getById(dto.getLabo_id()).get();

        return new CommissionModel(
                dto.getDateCommission(),
                dto.getHeure(),
                dto.getLieu(),
                labo,
                new ArrayList<>()
        );
    }

    public static CommissionDto toDto(CommissionModel commission, SujetService sujetService) {

        List<ProfesseurDto> listProf = commission.getCommissionProfesseurs().stream()
                .map(comProf -> ProfesseurDtoMapper.toDto(comProf.getProfesseur()))
                .toList();

        List<SujetDto> listSujets = listProf.stream()
                .flatMap( prof -> sujetService.getSujetsByProfID(prof.getId()).stream()
                        .map(SujetDtoMapper::toDto))
                .toList();

        return new CommissionDto(
                commission.getId(),
                commission.getDateCommission(),
                commission.getHeure(),
                true,
                commission.getLieu(),
                commission.getLaboratoire().getId(),
                listProf,
                listSujets
        );

    }



}
