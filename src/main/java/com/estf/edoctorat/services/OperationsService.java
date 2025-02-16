package com.estf.edoctorat.services;

import com.estf.edoctorat.config.CustomUserDetails;
import com.estf.edoctorat.dto.*;
import com.estf.edoctorat.mappers.CommissionDtoMapper;
import com.estf.edoctorat.mappers.InscriptionMapper;
import com.estf.edoctorat.models.*;
import com.estf.edoctorat.repositories.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OperationsService {

    private final FormationdoctoraleRepository formationDoctoraleRepo;
    private final ProfesseurRepository professeurRepo;
    private final SujetRepository sujetRepo;
    private final CommissionRepository commissionRepo;
    private final ExaminerRepository examinerRepo;
    private final InscriptionRepository inscriptionRepo;
    private final InscriptionMapper inscriptionMapper;
    private final SujetService sujetService;

    public List<CommissionDto> getAllCommissions() {
        return commissionRepo.findAll().stream()
                .map(commission -> CommissionDtoMapper.toDto(commission, sujetService))
                .collect(Collectors.toList());
    }

    public Page<InscriptionDto> getMesInscrits(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return inscriptionRepo.findAll(pageRequest)
                .map(this::mapToInscriptionDto);
    }

    public Page<ExaminerDto> getResultats(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return examinerRepo.findAll(pageRequest)
                .map(this::mapToExaminerDto);
    }

    public List<FormationdoctoraleDto> getAllFormationDoctorales() {
        return formationDoctoraleRepo.findAll().stream()
                .map(this::mapToFormationDoctoraleDto)
                .collect(Collectors.toList());
    }

    public List<ProfesseurDto> getAllProfesseurs() {
        return professeurRepo.findAll().stream()
                .map(this::mapToProfesseurDto)
                .collect(Collectors.toList());
    }

    public List<SujetDto> getAllSujets() {
        return sujetRepo.findAll().stream()
                .filter(sujet -> sujet != null)
                .map(sujet -> {
                    try {
                        return mapToSujetDtoNew(sujet);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(dto -> dto != null) 
                .collect(Collectors.toList());
    }

    public Sujet2Dto getSujetById(Long id) {
        return sujetRepo.findById(id)
                .map(this::mapToSujetDto)
                .orElseThrow(() -> new RuntimeException("Sujet not found"));
    }

    public Sujet2Dto createSujet(CreateSujetDto createSujetDto) {
        // Get the authenticated user using Spring Security's SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        UserModel user = userDetails.getUser();

        // Get the professor from the user
        // ProfesseurModel professeur = user.getProfesseur();
        // if (professeur == null) {
        // throw new RuntimeException("Only professors can create subjects");
        // }

        SujetModel sujet = new SujetModel();
        sujet.setTitre(createSujetDto.getTitre());
        sujet.setDescription(createSujetDto.getDescription());

        // Set the authenticated professor as the main professor
        // sujet.setProfesseur(professeur);

        // Get and set FormationDoctorale
        FormationdoctoraleModel formationDoctorale = formationDoctoraleRepo
                .findById(createSujetDto.getFormationDoctoraleId())
                .orElseThrow(() -> new RuntimeException("Formation doctorale not found"));
        sujet.setFormationDoctorale(formationDoctorale);

        if (createSujetDto.getCoDirecteurId() != null) {
            ProfesseurModel coDirecteur = professeurRepo
                    .findById(createSujetDto.getCoDirecteurId())
                    .orElseThrow(() -> new RuntimeException("Co-directeur not found"));
            sujet.setCodirecteur(coDirecteur);
        }

        sujet.setPublier(true);

        SujetModel savedSujet = sujetRepo.save(sujet);
        return mapToSujetDto(savedSujet);
    }

    public Sujet2Dto updateSujet(Long id, Sujet2Dto sujetDto) {
        if (!sujetRepo.existsById(id)) {
            throw new RuntimeException("Sujet not found");
        }
        SujetModel sujet = mapToSujetModel(sujetDto);
        sujet.setId(id);
        SujetModel updatedSujet = sujetRepo.save(sujet);
        return mapToSujetDto(updatedSujet);
    }

    public void deleteSujet(Long id) {
        if (!sujetRepo.existsById(id)) {
            throw new RuntimeException("Sujet not found");
        }
        sujetRepo.deleteById(id);
    }

    private SujetDto mapToSujetDtoNew(SujetModel model) {
        if (model == null)
            return null;

        return new SujetDto(
                model.getId(),
                model.getProfesseur() != null ? mapToProfesseurDto(model.getProfesseur()) : null,
                model.getFormationDoctorale() != null ? mapToFormationDoctoraleDto(model.getFormationDoctorale())
                        : null,
                model.getTitre(),
                model.getCodirecteur() != null ? mapToProfesseurDto(model.getCodirecteur()) : null,
                model.getDescription(),
                model.getPublier());
    }

    private FormationdoctoraleDto mapToFormationDoctoraleDto(FormationdoctoraleModel model) {
        return new FormationdoctoraleDto(
                model.getId(),
                model.getCed().getId(),
                model.getEtablissement().getIdEtablissement(),
                model.getAxeDeRecherche(),
                model.getPathImage(),
                model.getTitre(),
                model.getInitiale(),
                model.getDateAccreditation());
    }

    private ProfesseurDto mapToProfesseurDto(ProfesseurModel model) {
        return new ProfesseurDto(
                model.getId(),
                model.getUser().getFirst_name(),
                model.getUser().getLast_name());
    }

    private Sujet2Dto mapToSujetDto(SujetModel model) {
        return new Sujet2Dto(
                model.getId(),
                model.getProfesseur(),
                model.getCodirecteur(),
                model.getTitre(),
                model.getDescription(),
                model.getFormationDoctorale(),
                model.getPublier());
    }

    private SujetModel mapToSujetModel(Sujet2Dto dto) {
        SujetModel model = new SujetModel();
        model.setTitre(dto.getTitre());
        model.setDescription(dto.getDescription());
        model.setProfesseur(dto.getProfesseur());
        model.setCodirecteur(dto.getCoDirecteur());
        model.setFormationDoctorale(dto.getFormationDoctorale());
        model.setPublier(dto.isPublier());
        return model;
    }

    private InscriptionDto mapToInscriptionDto(InscriptionModel model) {
        return new InscriptionDto(
                model.getId(),
                mapToCandidatDto(model.getCandidat()),
                mapToSujetDto(model.getSujet()),
                model.getDateDisposeDossier().toString(),
                model.getRemarque(),
                (model.getValider() != 0),
                getCandidatPostulerPathFile(model.getCandidat(), model.getSujet()));
    }

    private String getCandidatPostulerPathFile(CandidatModel candidat, SujetModel sujet) {
        // You'll need to inject CandidatPostulerRepository and implement this method
        // to get the path_file from CandidatPostuler table
        return null; // Implement according to your needs
    }

    private CandidatDto mapToCandidatDto(CandidatModel model) {
        return new CandidatDto(
                model.getId(),
                model.getCne(),
                model.getPays().getNom(),
                model.getUser().getFirst_name(),
                model.getUser().getLast_name(),
                model.getUser().getEmail(),
                model.getCni(),
                model.getNomCandidatAr(),
                model.getPrenomCandidatAr(),
                model.getAdresse(),
                model.getAdresseAr(),
                model.getSexe(),
                model.getVilleDeNaissance(),
                model.getVilleDeNaissanceAr(),
                model.getVille(),
                model.getDateDeNaissance(),
                model.getTypeDeHandicape(),
                model.getAcademie(),
                model.getTelCandidat(),
                model.getPathCv(),
                model.getPathPhoto(),
                model.getEtatDossier(),
                model.getSituation_familiale(),
                model.isFonctionnaire());
    }

    private ExaminerDto mapToExaminerDto(ExaminerModel model) {
        // Implement this mapping method based on your ExaminerDto structure
        return null; // Implement according to your needs
    }
}