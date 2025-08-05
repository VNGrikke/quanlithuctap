package java_web.quanlithuctap.service.impl;

import jakarta.transaction.Transactional;
import java_web.quanlithuctap.dto.StudentRequest;
import java_web.quanlithuctap.dto.StudentResponse;
import java_web.quanlithuctap.entity.Mentor;
import java_web.quanlithuctap.entity.Student;
import java_web.quanlithuctap.entity.User;
import java_web.quanlithuctap.repository.MentorRepository;
import java_web.quanlithuctap.repository.StudentRepository;
import java_web.quanlithuctap.repository.UserRepository;
import java_web.quanlithuctap.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepo;
    private final UserRepository userRepo;
    private final MentorRepository mentorRepo;

    @Override
    public List<StudentResponse> getAll() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepo.findByUsername(username).orElseThrow();

        if (currentUser.getRole() == User.Role.MENTOR) {
            return studentRepo.findByMentor_MentorId(currentUser.getId())
                    .stream().map(this::toDto).collect(Collectors.toList());
        }

        return studentRepo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public StudentResponse getById(Integer id) {
        Student student = studentRepo.findById(id).orElseThrow();
        User currentUser = userRepo.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow();

        if (currentUser.getRole() == User.Role.STUDENT &&
                !student.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Bạn không được phép xem sinh viên khác.");
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

        if (studentRepo.existsById(user.getId())) {
            throw new IllegalArgumentException("User này đã được gán cho sinh viên.");
        }

        Mentor mentor = mentorRepo.findById(request.getMentorId()).orElse(null);

        Student student = Student.builder()
                .studentId(user.getId())
                .user(user)  // user là entity managed
                .studentCode(request.getStudentCode())
                .major(request.getMajor())
                .studentClass(request.getClazz())
                .dateOfBirth(request.getDateOfBirth())
                .createdAt(LocalDate.now())
                .updatedAt(LocalDate.now())
                .address(request.getAddress())
                .mentor(mentor)
                .build();

        studentRepo.save(student);

        return toDto(student);
    }

    @Override
    public StudentResponse update(Integer id, StudentRequest request) {
        Student student = studentRepo.findById(id).orElseThrow();
        User currentUser = userRepo.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow();

        if (currentUser.getRole() == User.Role.STUDENT &&
                !student.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Bạn không có quyền cập nhật thông tin này.");
        }

        student.setMajor(request.getMajor());
        student.setStudentClass(request.getClazz());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setAddress(request.getAddress());
        student.setMentor(mentorRepo.findById(request.getMentorId()).orElse(null));
        student.setUpdatedAt(LocalDate.now());
        return toDto(student);
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
