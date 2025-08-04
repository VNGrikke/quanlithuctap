package java_web.quanlithuctap.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "assessment_rounds")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssessmentRound {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roundId;

    @ManyToOne
    @JoinColumn(name = "phase_id", nullable = false)
    private InternshipPhase phase;

    @Column(nullable = false)
    private String roundName;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    private String description;

    private Boolean isActive = true;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

