package java_web.quanlithuctap.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class APIResponse<T> {
    private T data;
    private boolean success;
    private String message;
    private T errors;
    private LocalDateTime timeStamp;
}
