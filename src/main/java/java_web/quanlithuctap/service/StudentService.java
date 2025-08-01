package java_web.quanlithuctap.service;

import java_web.quanlithuctap.entity.Student;

import java.util.Optional;

public interface StudentService {
    Student create(Student student);
    Optional<Student> findById(Integer id);
    Optional<Student> findByStudentCode(String code);
}
