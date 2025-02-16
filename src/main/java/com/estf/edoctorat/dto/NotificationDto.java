package com.estf.edoctorat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Notification Data Transfer Object")
public class NotificationDto {
    @Schema(description = "Unique identifier of the notification")
    private Long id;

    @Schema(description = "Type of notification")
    private String type;

    @Schema(description = "Associated subject details")
    private SujetDto sujet;

    @Schema(description = "Associated commission details")
    private CommissionDto commission;

    @Schema(description = "ID of the candidate receiving the notification")
    private Long candidatId;
}