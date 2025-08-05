package java_web.quanlithuctap.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationCriteriaDTO {

    @NotBlank(message = "criterionId không được để trống")
    private Integer criterionId;
    @NotBlank(message = "criterionName không được để trống")
    private String criterionName;
    @NotBlank(message = "description không được để trống")
    private String description;
    @NotBlank(message = "maxScore không được để trống")
    private BigDecimal maxScore;
}
