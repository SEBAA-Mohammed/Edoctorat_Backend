package com.estf.edoctorat.services;

import com.estf.edoctorat.dto.CandidatDto;
import com.estf.edoctorat.models.CandidatModel;
import com.estf.edoctorat.models.PaysModel;
import com.estf.edoctorat.models.UserModel;
import com.estf.edoctorat.repositories.CandidatRepository;
import com.estf.edoctorat.repositories.PaysRepository;
import com.estf.edoctorat.repositories.UserRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidatService {
    @Autowired
    private CandidatRepository candidatRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PaysRepository paysRepository;

    public List<CandidatModel> getCandidats() {
        return candidatRepository.findAll();
    }

    public Optional<CandidatModel> getCandidatById(Long id) {
        return candidatRepository.findById(id);
    }

    public List<CandidatModel> getCandidatByName(String name) {
        return candidatRepository.findByNomCandidatAr(name);
    }

    public CandidatModel createCandidat(CandidatModel candidat) {
        return candidatRepository.save(candidat);
    }

    public void deleteCandidat(Long id) {
        if (candidatRepository.existsById(id)) {
            candidatRepository.deleteById(id);
        } else {
            throw new RuntimeException("Candidat not found with ID " + id);
        }
    }

    public CandidatModel updateCandidat(Long id, CandidatModel updatedCandidat) {
        return candidatRepository.findById(id).map(candidat -> {
            candidat.setCne(updatedCandidat.getCne());
            candidat.setCni(updatedCandidat.getCni());
            candidat.setNomCandidatAr(updatedCandidat.getNomCandidatAr());
            candidat.setPrenomCandidatAr(updatedCandidat.getPrenomCandidatAr());
            candidat.setAdresseAr(updatedCandidat.getAdresseAr());
            candidat.setAdresse(updatedCandidat.getAdresse());
            candidat.setSexe(updatedCandidat.getSexe());
            candidat.setVilleDeNaissance(updatedCandidat.getVilleDeNaissance());
            candidat.setVilleDeNaissanceAr(updatedCandidat.getVilleDeNaissanceAr());
            candidat.setVille(updatedCandidat.getVille());
            candidat.setDateDeNaissance(updatedCandidat.getDateDeNaissance());
            candidat.setTypeDeHandicape(updatedCandidat.getTypeDeHandicape());
            candidat.setAcademie(updatedCandidat.getAcademie());
            candidat.setTelCandidat(updatedCandidat.getTelCandidat());
            candidat.setPathCv(updatedCandidat.getPathCv());
            candidat.setPathPhoto(updatedCandidat.getPathPhoto());
            candidat.setEtatDossier(updatedCandidat.getEtatDossier());
            candidat.setSituation_familiale(updatedCandidat.getSituation_familiale());
            candidat.setFonctionnaire(updatedCandidat.isFonctionnaire());

            if (updatedCandidat.getUser() != null && updatedCandidat.getUser().getId() != null) {
                Long userId = updatedCandidat.getUser().getId();
                UserModel user = userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User not found with id " + userId));
                candidat.setUser(user);
            } else {
                candidat.setUser(null);
            }

            if (updatedCandidat.getPays() != null && updatedCandidat.getPays().getId() != null) {
                Long paysId = updatedCandidat.getPays().getId();
                PaysModel pays = paysRepository.findById(paysId)
                        .orElseThrow(() -> new RuntimeException("Pays not found with id " + paysId));
                candidat.setPays(pays);
            } else {
                candidat.setPays(null);
            }

            return candidatRepository.save(candidat);
        }).orElseThrow(() -> new RuntimeException("Candidat not found with id " + id));
    }

    public CandidatDto getCandidatInfo(UserModel user) {
        CandidatModel candidat = candidatRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Candidate not found for user"));

        CandidatDto dto = new CandidatDto();
        // Map basic fields
        BeanUtils.copyProperties(candidat, dto);

        // Map user fields
        dto.setNom(candidat.getUser().getLast_name());
        dto.setPrenom(candidat.getUser().getFirst_name());
        dto.setEmail(candidat.getUser().getEmail());

        if (candidat.getPays() != null) {
            dto.setPays(candidat.getPays().getNom());
        }

        return dto;
    }

    public CandidatModel getCandidatProfile(Long id) {
        return candidatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidate not found with ID " + id));
    }
}
