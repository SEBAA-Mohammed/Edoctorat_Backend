package com.estf.edoctorat.mappers;

import com.estf.edoctorat.dto.CandidatDto;
import com.estf.edoctorat.models.CandidatModel;
import com.estf.edoctorat.models.UserModel;
import com.estf.edoctorat.services.UserService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CandidatDtoMapper {

    public static CandidatDto toDto(CandidatModel candidat){

        UserModel user = candidat.getUser();

        return new CandidatDto(
                candidat.getId(),
                candidat.getCne(),
                candidat.getPays().getNom(),
                user.getLast_name(),
                user.getLast_name(),
                user.getEmail(),
                candidat.getCni(),
                candidat.getNomCandidatAr(),
                candidat.getPrenomCandidatAr(),
                candidat.getAdresse(),
                candidat.getAdresseAr(),
                candidat.getSexe(),
                candidat.getVilleDeNaissance(),
                candidat.getVilleDeNaissanceAr(),
                candidat.getVille(),
                candidat.getDateDeNaissance(),
                candidat.getTypeDeHandicape(),
                candidat.getAcademie(),
                candidat.getTelCandidat(),
                candidat.getPathCv(),
                candidat.getPathPhoto(),
                candidat.getEtatDossier(),
                candidat.getSituation_familiale(),
                candidat.isFonctionnaire()
        );

    }
    public static CandidatModel toEntity(CandidatDto dto) {
        if (dto == null) {
            return null;
        }

        CandidatModel candidat = new CandidatModel();
        candidat.setId(dto.getId());
        candidat.setCne(dto.getCne());
        candidat.setCni(dto.getCin());
        candidat.setNomCandidatAr(dto.getNomCandidatAr());
        candidat.setPrenomCandidatAr(dto.getPrenomCandidatAr());
        candidat.setAdresse(dto.getAdresse());
        candidat.setAdresseAr(dto.getAdresseAr());
        candidat.setSexe(dto.getSexe());
        candidat.setVilleDeNaissance(dto.getVilleDeNaissance());
        candidat.setVilleDeNaissanceAr(dto.getVilleDeNaissanceAr());
        candidat.setVille(dto.getVille());

        // Handle date conversion
        if (dto.getDateDeNaissance() != null) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
                LocalDateTime localDateTime = LocalDateTime.parse(dto.getDateDeNaissance().toString(), formatter);

                // Convert LocalDateTime to Instant, then to Date
                Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

                candidat.setDateDeNaissance(date);
            } catch (Exception e) {
                // For debugging
            }
        }


        candidat.setTypeDeHandicape(dto.getTypeDeHandicape());
        candidat.setAcademie(dto.getAcademie());
        candidat.setTelCandidat(dto.getTelCandidat());
        candidat.setPathCv(dto.getPathCv());
        candidat.setPathPhoto(dto.getPathPhoto());
        candidat.setEtatDossier(dto.getEtatDossier());
        candidat.setSituation_familiale(dto.getSituation_familiale());
        candidat.setFonctionnaire("Oui".equalsIgnoreCase(dto.getFonctionnaire()?"Oui" : "Non"));

        // Note: You'll need to set up the User and Pays relationships separately
        // as they require fetching from their respective repositories
        return candidat;
    }

}
