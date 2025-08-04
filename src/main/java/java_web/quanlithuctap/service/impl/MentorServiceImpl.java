package java_web.quanlithuctap.service.impl;

import java_web.quanlithuctap.dto.MentorRequest;
import java_web.quanlithuctap.dto.MentorResponse;
import java_web.quanlithuctap.entity.Mentor;
import java_web.quanlithuctap.entity.User;
import java_web.quanlithuctap.repository.MentorRepository;
import java_web.quanlithuctap.repository.UserRepository;
import java_web.quanlithuctap.service.MentorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MentorServiceImpl implements MentorService {

    private final MentorRepository mentorRepo;
    private final UserRepository userRepo;

    @Override
    public List<MentorResponse> getAll() {
        return mentorRepo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public MentorResponse getById(Integer id) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Mentor mentor = mentorRepo.findById(id).orElseThrow();

        if (!mentor.getUser().getUsername().equals(currentUsername)) {
            throw new RuntimeException("Bạn chỉ được xem thông tin của mình.");
        }

        return toDto(mentor);
    }

    @Override
    public MentorResponse createMentor(MentorRequest request) {
        User user = userRepo.findById(request.getMentorId())
                .orElseThrow(() -> new IllegalArgumentException("User không tồn tại"));

        if (!user.getRole().equals(User.Role.MENTOR)) {
            throw new RuntimeException("User không có vai trò MENTOR");
        }

        if (mentorRepo.existsById(user.getId())) {
            throw new RuntimeException("Mentor đã tồn tại");
        }

        Mentor mentor = Mentor.builder()
                .mentorId(user.getId())
                .user(user)
                .department(request.getDepartment())
                .academicRank(request.getAcademicRank())
                .build();

        mentorRepo.save(mentor);
        return toDto(mentor);
    }

    @Override
    public void updateMentor(Integer id, MentorRequest request) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Mentor mentor = mentorRepo.findById(id).orElseThrow();

        if (!mentor.getUser().getUsername().equals(currentUsername)
                && !userRepo.findByUsername(currentUsername).get().getRole().equals(User.Role.ADMIN)) {
            throw new RuntimeException("Bạn không có quyền cập nhật mentor này");
        }

        mentor.setDepartment(request.getDepartment());
        mentor.setAcademicRank(request.getAcademicRank());
        mentorRepo.save(mentor);
    }

    private MentorResponse toDto(Mentor m) {
        return MentorResponse.builder()
                .mentorId(m.getMentorId())
                .fullName(m.getUser().getFullName())
                .email(m.getUser().getEmail())
                .department(m.getDepartment())
                .academicRank(m.getAcademicRank())
                .build();
    }
}
