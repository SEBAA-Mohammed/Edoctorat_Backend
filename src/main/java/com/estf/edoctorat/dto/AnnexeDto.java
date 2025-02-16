package com.estf.edoctorat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data Transfer Object for document attachments")
public class AnnexeDto {
    @Schema(description = "Type of attachment (e.g., diploma, transcript)", example = "DIPLOMA")
    private String typeAnnexe;

    @Schema(description = "Title or name of the attachment", example = "Bachelor's Degree Certificate")
    private String titre;

    @Schema(description = "File path or location of the attachment", example = "/uploads/diplomas/user123/diploma.pdf")
    private String pathFile;
}
