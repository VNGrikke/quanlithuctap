//package java_web.quanlithuctap.entity;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.DecimalMin;
//import lombok.*;
//import org.springframework.format.annotation.DateTimeFormat;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//@Data
//@Entity
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@Table(name = "round_criteria", uniqueConstraints = {
//        @UniqueConstraint(columnNames = {"RoundID", "CriterionID"})
//})
//public class RoundCriteria {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "RoundCriteriaID")
//    private Integer id;
//
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "RoundID", nullable = false)
//    private AssessmentRound round;
//
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "CriterionID", nullable = false)
//    private EvaluationCriteria criterion;
//
//    @Column(name = "Weight", nullable = false, precision = 5, scale = 2)
//    @DecimalMin(value = "0.01", message = "Weight must be greater than 0")
//    private BigDecimal weight;
//
//    @Column(name = "CreatedAt", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
//    private LocalDate createdAt;
//
//    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
//    @Column(name = "UpdatedAt", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
//    private LocalDate updatedAt;
//
//    @Version
//    @Column(name = "version")
//    private Integer version;
//}
