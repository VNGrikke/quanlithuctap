package java_web.quanlithuctap.service;

import java_web.quanlithuctap.dto.StudentRequest;
import java_web.quanlithuctap.dto.StudentResponse;
import java_web.quanlithuctap.dto.UserResponse;

import java.util.List;

public interface StudentService {
    List<StudentResponse> getAll();
    StudentResponse getById(Integer id);
    StudentResponse create(StudentRequest request);
    StudentResponse update(Integer id, StudentRequest request);
}
