package com.estf.edoctorat.mappers;

import com.estf.edoctorat.dto.FormationdoctoraleDto;
import com.estf.edoctorat.dto.Professeur2Dto;
import com.estf.edoctorat.dto.Sujet2Dto;
import com.estf.edoctorat.dto.SujetDto;
import com.estf.edoctorat.models.FormationdoctoraleModel;
import com.estf.edoctorat.models.ProfesseurModel;
import com.estf.edoctorat.models.SujetModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SujetMapper {
    public static Sujet2Dto toDto(SujetModel sujet) {
        return new Sujet2Dto(
                sujet.getId(),
                sujet.getProfesseur(),
                sujet.getCodirecteur(),
                sujet.getTitre(),
                sujet.getDescription(),
                sujet.getFormationDoctorale(),
                sujet.getPublier() == 1
        );
    }

    public static List<Sujet2Dto> toDtoList(List<SujetModel> sujets) {
        return sujets.stream()
                .map(SujetMapper::toDto)
                .collect(Collectors.toList());
    }
}
