//package java_web.quanlithuctap.entity;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import jakarta.persistence.*;
//import lombok.*;
//import org.springframework.format.annotation.DateTimeFormat;
//
//import java.time.LocalDateTime;
//
//@Data
//@Entity
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@Table(name = "internship_assignments", uniqueConstraints = {
//        @UniqueConstraint(columnNames = {"StudentID", "PhaseID"})
//})
//public class InternshipAssignment {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "AssignmentID")
//    private Integer id;
//
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "StudentID", nullable = false)
//    private Student student;
//
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "MentorID", nullable = false)
//    private User mentor;
//
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "PhaseID", nullable = false)
//    private InternshipPhases phase;
//
//    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
//    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
//    @Column(name = "AssignedDate", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
//    private LocalDateTime assignedDate;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "Status", nullable = false, length = 20)
//    private AssignmentStatus status = AssignmentStatus.PENDING;
//
//    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
//    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
//    @Column(name = "CreatedAt", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
//    private LocalDateTime createdAt;
//
//    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
//    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
//    @Column(name = "UpdatedAt", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
//    private LocalDateTime updatedAt;
//
//    public enum AssignmentStatus {
//        PENDING,
//        IN_PROGRESS,
//        COMPLETED,
//        CANCELLED
//    }
//
//    @PrePersist
//    protected void onCreate() {
//        LocalDateTime now = LocalDateTime.now();
//        createdAt = now;
//        updatedAt = now;
//        assignedDate = now;
//    }
//
//    @PreUpdate
//    protected void onUpdate() {
//        updatedAt = LocalDateTime.now();
//    }
//}
