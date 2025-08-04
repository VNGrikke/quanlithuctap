package java_web.quanlithuctap.service.impl;

import java_web.quanlithuctap.dto.*;
import java_web.quanlithuctap.entity.*;
import java_web.quanlithuctap.repository.*;
import java_web.quanlithuctap.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepo;
    private final UserRepository userRepo;
    private final MentorRepository mentorRepo;

    @Override
    public List<StudentResponse> getAll() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByUsername(currentUsername).orElseThrow();

        if (user.getRole() == User.Role.MENTOR) {
            return studentRepo.findByMentorId(user.getId())
                    .stream().map(this::toDto).collect(Collectors.toList());
        }

        return studentRepo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public StudentResponse getById(Integer id) {
        Student student = studentRepo.findById(id).orElseThrow();
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepo.findByUsername(currentUsername).orElseThrow();

        if (currentUser.getRole() == User.Role.STUDENT && !student.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Bạn không được phép truy cập.");
        }

        return toDto(student);
    }

    @Override
    public StudentResponse create(StudentRequest request) {
        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User không tồn tại"));
        if (user.getRole() != User.Role.STUDENT) {
            throw new IllegalArgumentException("User phải có role STUDENT");
        }

        Mentor mentor = mentorRepo.findById(request.getMentorId()).orElse(null);

        Student student = Student.builder()
                .user(user)
                .studentCode(request.getStudentCode())
                .major(request.getMajor())
                .studentClass(request.getClazz())
                .dateOfBirth(request.getDateOfBirth())
                .address(request.getAddress())
                .mentor(mentor)
                .build();

        studentRepo.save(student);
        return toDto(student);
    }

    @Override
    public StudentResponse update(Integer id, StudentRequest request) {
        Student student = studentRepo.findById(id).orElseThrow();
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepo.findByUsername(currentUsername).orElseThrow();

        if (currentUser.getRole() == User.Role.STUDENT && !student.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Bạn không có quyền cập nhật.");
        }

        student.setMajor(request.getMajor());
        student.setStudentClass(request.getClazz());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setAddress(request.getAddress());
        student.setMentor(mentorRepo.findById(request.getMentorId()).orElse(null));

        return toDto(studentRepo.save(student));
    }

    private StudentResponse toDto(Student s) {
        return StudentResponse.builder()
                .studentId(s.getStudentId())
                .studentCode(s.getStudentCode())
                .fullName(s.getUser().getFullName())
                .email(s.getUser().getEmail())
                .major(s.getMajor())
                .clazz(s.getStudentClass())
                .dateOfBirth(s.getDateOfBirth())
                .address(s.getAddress())
                .mentorName(s.getMentor() != null ? s.getMentor().getUser().getFullName() : null)
                .build();
    }
}
