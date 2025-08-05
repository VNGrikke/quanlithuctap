package java_web.quanlithuctap.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse {
    private Integer studentId;
    private String studentCode;
    private String fullName;
    private String major;
    private String clazz;
    private LocalDate dateOfBirth ;
    private String address;
    private String email;
    private String mentorName;
}
