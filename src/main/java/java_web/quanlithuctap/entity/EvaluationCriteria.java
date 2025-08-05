package java_web.quanlithuctap.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "evaluation_criteria")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvaluationCriteria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer criterionId;

    @Column(nullable = false, unique = true)
    private String criterionName;

    private String description;

    @Column(nullable = false)
    private BigDecimal maxScore;

    private LocalDate createdAt;
    private LocalDate updatedAt;

    @Version
    @Column(name = "version")
    private Integer version;

}
