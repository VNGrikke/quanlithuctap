package java_web.quanlithuctap.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "students")
public class Student {

    @Id
    @Column(name = "student_id")
    private Integer studentId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "student_id")
    private User user;

    @Column(name = "student_code", nullable = false, unique = true, length = 20)
    private String studentCode;

    @Column(name = "major", length = 100)
    private String major;

    @Column(name = "class", length = 50)
    private String studentClass;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "address", length = 255)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_id")
    private Mentor mentor;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "CreatedAt", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "UpdatedAt", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
