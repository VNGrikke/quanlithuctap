package java_web.quanlithuctap.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MentorResponse {
    private Integer mentorId;
    private String fullName;
    private String department;
    private String academicRank;
    private String email;
}
