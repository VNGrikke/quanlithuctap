package java_web.quanlithuctap.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequest {
    private Integer userId;
    private String studentCode;
    private String major;
    private String clazz;
    private LocalDate dateOfBirth;
    private String address;
    private Integer mentorId;
}
