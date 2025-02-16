package com.estf.edoctorat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Candidate Data Transfer Object containing personal and academic information")
public class CandidatDto {
    @Schema(description = "Unique identifier of the candidate")
    private Long id;

    @Schema(description = "National Student ID number", example = "H123456")
    private String cne;

    @Schema(description = "Country of residence", example = "Morocco")
    private String pays;

    @Schema(description = "Last name in Latin characters", example = "Smith")
    private String nom;

    @Schema(description = "First name in Latin characters", example = "John")
    private String prenom;

    @Schema(description = "Email address", example = "john.smith@example.com")
    private String email;

    @Schema(description = "National ID number", example = "AB123456")
    private String cin;

    @Schema(description = "Last name in Arabic")
    private String nomCandidatAr;

    @Schema(description = "First name in Arabic")
    private String prenomCandidatAr;

    @Schema(description = "Address in Latin characters")
    private String adresse;

    @Schema(description = "Address in Arabic")
    private String adresseAr;

    @Schema(description = "Gender", example = "M")
    private String sexe;

    @Schema(description = "Birth city in Latin characters")
    private String villeDeNaissance;

    @Schema(description = "Birth city in Arabic")
    private String villeDeNaissanceAr;

    @Schema(description = "Current city of residence")
    private String ville;

    @Schema(description = "Date of birth", example = "1990-01-01")
    private Date dateDeNaissance;

    @Schema(description = "Type of disability, if any")
    private String typeDeHandicape;

    @Schema(description = "Academic region")
    private String academie;

    @Schema(description = "Phone number", example = "+212600000000")
    private String telCandidat;

    @Schema(description = "Path to CV file", example = "/uploads/cv/user123.pdf")
    private String pathCv;

    @Schema(description = "Path to photo file", example = "/uploads/photos/user123.jpg")
    private String pathPhoto;

    @Schema(description = "Application status code", example = "1")
    private Integer etatDossier;

    @Schema(description = "Marital status", example = "SINGLE")
    private String situation_familiale;

    @Schema(description = "Whether the candidate is a civil servant", example = "false")
    private Boolean fonctionnaire;
}