package java_web.quanlithuctap.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
public class AssessmentRoundDTO {

    @NotBlank(message = "roundId không được để trống")
    private Integer roundId;
    @NotBlank(message = "phaseId không được để trống")
    private Integer phaseId;
    @NotBlank(message = "roundName không được để trống")
    private String roundName;
    @NotBlank(message = "startDate không được để trống")
    private LocalDate startDate;
    @NotBlank(message = "endDate không được để trống")
    private LocalDate endDate;
    @NotBlank(message = "description không được để trống")
    private String description;
    private Boolean isActive;
}
