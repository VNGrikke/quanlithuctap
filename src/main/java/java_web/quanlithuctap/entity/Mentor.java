package java_web.quanlithuctap.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "mentors")
public class Mentor {

    @Id
    @Column(name = "mentor_id")
    private Integer mentorId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "mentor_id")
    private User user;

    @Column(name = "department", length = 100)
    private String department;

    @Column(name = "academic_rank", length = 50)
    private String academicRank;

    @OneToMany(mappedBy = "mentor")
    private List<Student> students;

    @Column(name = "CreatedAt", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDate createdAt;


    @Column(name = "UpdatedAt", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDate updatedAt;

    @Version
    @Column(name = "version")
    private Integer version;

}
