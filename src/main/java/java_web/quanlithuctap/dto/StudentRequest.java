package java_web.quanlithuctap.dto;

import lombok.*;
import java.time.LocalDate;

@Data
public class StudentRequest {
    private Integer userId;      // liên kết với User có role STUDENT
    private String studentCode;
    private String major;
    private String clazz;
    private LocalDate dateOfBirth;
    private String address;
    private Integer mentorId;   // phân công mentor
}
