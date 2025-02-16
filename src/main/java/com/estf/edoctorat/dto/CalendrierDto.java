package com.estf.edoctorat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.Date;

@Data
@Schema(description = "Calendar Data Transfer Object for managing date ranges")
public class CalendrierDto {
    @Schema(description = "Start date of the period", example = "2024-01-01", required = true)
    private Date dateDebut;

    @Schema(description = "End date of the period", example = "2024-12-31", required = true)
    private Date dateFin;
}
