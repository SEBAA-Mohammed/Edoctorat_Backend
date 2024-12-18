package com.estf.edoctorat.services;

import com.estf.edoctorat.models.CandidatModel;
import com.estf.edoctorat.repositories.CandidatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidatService {
    @Autowired
    private CandidatRepository candidatRepository;

//    public List<CandidatModel> candidats;
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
            candidat.setPay_id(updatedCandidat.getPay_id());
            candidat.setUser_id(updatedCandidat.getUser_id());
            candidat.setFonctionaire(updatedCandidat.getFonctionaire());
            return candidatRepository.save(candidat);
        }).orElseThrow(() -> new RuntimeException("Candidat not found with id " + id));
    }

}
