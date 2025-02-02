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
    public static SujetModel toEntity(Sujet2Dto dto) {
        if (dto == null) {
            return null;
        }

        SujetModel sujet = new SujetModel();
        sujet.setId(dto.getId());
        sujet.setTitre(dto.getTitre());
        sujet.setDescription(dto.getDescription());
        sujet.setProfesseur(dto.getProfesseur());
        sujet.setCodirecteur(dto.getCoDirecteur());
        sujet.setFormationDoctorale(dto.getFormationDoctorale());
        sujet.setPublier(dto.isPublier());

        return sujet;
    }

    public static Sujet2Dto toDto(SujetModel model) {
        if (model == null) {
            return null;
        }

        return new Sujet2Dto(
                model.getId(),
                model.getProfesseur(),
                model.getCodirecteur(),
                model.getTitre(),
                model.getDescription(),
                model.getFormationDoctorale(),
                model.getPublier()
        );
    }

    public static List<Sujet2Dto> toDtoList(List<SujetModel> sujets) {
        return sujets.stream()
                .map(SujetMapper::toDto)
                .collect(Collectors.toList());
    }
}
