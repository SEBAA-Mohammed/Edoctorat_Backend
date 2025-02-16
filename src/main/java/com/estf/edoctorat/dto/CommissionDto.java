package com.estf.edoctorat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data Transfer Object for Commission details")
public class CommissionDto {

    @Schema(description = "Unique identifier of the commission", example = "1")
    private Long id;

    @Schema(description = "Date when the commission meets", example = "2024-03-15")
    private Date dateCommission;

    @Schema(description = "Time of the commission meeting", example = "14:30:00")
    private Date heure;

    @Schema(description = "Whether the commission has been validated", example = "true")
    private boolean valider;

    @Schema(description = "Location where the commission meets", example = "Room A-101")
    private String lieu;

    @Schema(description = "ID of the laboratory this commission belongs to", example = "1")
    private Long labo;

    @Schema(description = "List of professors participating in the commission")
    private List<ProfesseurDto> participants;

    @Schema(description = "List of subjects to be evaluated by the commission")
    private List<SujetDto> sujets;
}
