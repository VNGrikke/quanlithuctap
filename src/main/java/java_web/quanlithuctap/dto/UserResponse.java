package java_web.quanlithuctap.dto;

import lombok.*;

@Data
@Builder
public class UserResponse {
    private Integer id;
    private String username;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String role;
}