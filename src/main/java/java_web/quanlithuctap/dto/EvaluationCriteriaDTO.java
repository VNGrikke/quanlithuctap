package java_web.quanlithuctap.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationCriteriaDTO {
    private Integer criterionId;
    private String criterionName;
    private String description;
    private BigDecimal maxScore;
}
