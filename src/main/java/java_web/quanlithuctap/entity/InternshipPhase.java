package java_web.quanlithuctap.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "internship_phases")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternshipPhase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer phaseId;

    @Column(nullable = false, unique = true)
    private String phaseName;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    private String description;

    private LocalDate createdAt;
    private LocalDate updatedAt;

    @Version
    @Column(name = "version")
    private Integer version;

}
