package java_web.quanlithuctap.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InternshipPhaseDTO {

    @NotBlank(message = "phaseId không được để trống")
    private Integer phaseId;
    @NotBlank(message = "phaseName không được để trống")
    private String phaseName;
    @NotBlank(message = "startDate không được để trống")
    private LocalDate startDate;
    @NotBlank(message = "endDate không được để trống")
    private LocalDate endDate;
    @NotBlank(message = "description không được để trống")
    private String description;
}
