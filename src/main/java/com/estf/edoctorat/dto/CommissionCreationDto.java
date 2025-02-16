package com.estf.edoctorat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data Transfer Object for creating a new commission")
public class CommissionCreationDto {

    @Schema(description = "Date when the commission meets", example = "2024-03-15", required = true)
    private Date dateCommission;

    @Schema(description = "Time of the commission meeting", example = "14:30:00", required = true)
    private Date heure;

    @Schema(description = "Location where the commission will meet", example = "Room A-101", required = true)
    private String lieu;

    @Schema(description = "List of professor IDs who are members of the commission", example = "[1, 2, 3]", required = true)
    private List<Long> professeurs;

    @Schema(description = "List of subject IDs to be evaluated by the commission", example = "[101, 102, 103]", required = true)
    private List<Long> sujets_ids;

    @Schema(description = "ID of the laboratory this commission belongs to", example = "1", required = true)
    private long labo_id;
}
