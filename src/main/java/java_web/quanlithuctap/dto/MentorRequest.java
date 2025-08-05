package java_web.quanlithuctap.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MentorRequest {

    private Integer mentorId;
    private String department;
    private String academicRank;
}
