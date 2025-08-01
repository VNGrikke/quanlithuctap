package java_web.quanlithuctap.service.impl;

import java_web.quanlithuctap.entity.Student;
import java_web.quanlithuctap.entity.User;
import java_web.quanlithuctap.repository.StudentRepository;
import java_web.quanlithuctap.repository.UserRepository;
import java_web.quanlithuctap.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    @Override
    public Student create(Student student) {
        User user = userRepository.findById(Long.valueOf(student.getId()))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (user.getRole() != User.Role.STUDENT) {
            throw new IllegalArgumentException("User must have STUDENT role");
        }

        student.setUser(user);
        return studentRepository.save(student);
    }

    @Override
    public Optional<Student> findById(Integer id) {
        return studentRepository.findById(id);
    }

    @Override
    public Optional<Student> findByStudentCode(String code) {
        return studentRepository.findByStudentCode(code);
    }
}
