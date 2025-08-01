package java_web.quanlithuctap.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
//@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "assessment_results", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"AssignmentID", "RoundID", "CriterionID"})
})
public class AssessmentResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ResultID")
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "AssignmentID", nullable = false)
    private InternshipAssignment assignment;

    @ManyToOne(optional = false)
    @JoinColumn(name = "RoundID", nullable = false)
    private AssessmentRounds round;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CriterionID", nullable = false)
    private EvaluationCriteria criterion;

    @Column(name = "Score", nullable = false, precision = 5, scale = 2)
    @DecimalMin(value = "0.00", message = "Score must be >= 0")
    private BigDecimal score;

    @Column(name = "Comments", columnDefinition = "TEXT")
    private String comments;

    @ManyToOne(optional = false)
    @JoinColumn(name = "EvaluatedBy", nullable = false)
    private User mentor;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "EvaluationDate", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime evaluationDate;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "CreatedAt", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "UpdatedAt", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
        evaluationDate = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
