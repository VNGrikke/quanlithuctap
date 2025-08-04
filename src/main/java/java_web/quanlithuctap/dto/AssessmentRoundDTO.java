package java_web.quanlithuctap.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
public class AssessmentRoundDTO {
    private Integer roundId;
    private Integer phaseId;
    private String roundName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private Boolean isActive;
}
