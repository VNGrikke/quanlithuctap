package java_web.quanlithuctap.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InternshipPhaseDTO {
    private Integer phaseId;
    private String phaseName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
}
