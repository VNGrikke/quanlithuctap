package java_web.quanlithuctap.service.impl;

import java_web.quanlithuctap.dto.LoginRequest;
import java_web.quanlithuctap.dto.RegisterRequest;
import java_web.quanlithuctap.dto.UserResponse;
import java_web.quanlithuctap.entity.User;
import java_web.quanlithuctap.exception.ConflictException;
import java_web.quanlithuctap.mapper.UserMapper;
import java_web.quanlithuctap.repository.UserRepository;
import java_web.quanlithuctap.service.UserService;
import java_web.quanlithuctap.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;


    @Override
    public String login(LoginRequest request) {
        User user = userRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Mật khẩu không đúng");
        }
        return jwtUtils.generateAccessToken(user.getUsername(), user.getRole().name());
    }




    @Override
    public UserResponse register(RegisterRequest request) {
        if (userRepo.existsByUsername(request.getUsername())) {
            throw new ConflictException("Username đã tồn tại");
        }

        if (userRepo.existsByEmail(request.getEmail())) {
            throw new ConflictException("Email đã tồn tại");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .role(User.Role.valueOf(request.getRole().toUpperCase()))
                .isActive(true)
                .createdAt(LocalDate.now().atStartOfDay())
                .updatedAt(LocalDate.now().atStartOfDay())
                .build();

        userRepo.save(user);
        return userMapper.toDto(user);
    }
    @Override
    public UserResponse getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepo.findByUsername(username)
                .map(this::mapToResponse)
                .orElseThrow();
    }

    @Override
    public List<UserResponse> getAll() {
        return userRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public UserResponse getById(Integer id) {
        return userRepo.findById(id)
                .map(this::mapToResponse)
                .orElseThrow();
    }

//    @Override
//    public void updateActive(Integer id) {
//        User user = userRepo.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("User not found"));
//        if (user.getRole() != User.Role.ADMIN) {
//            throw new IllegalArgumentException("User must have ADMIN role");
//        }
//        if (user.getIsActive()) {
//            throw new IllegalArgumentException("User is already active");
//        }else {
//            user.setIsActive(true);
//            userRepo.save(user);
//        }
//    }

    @Override
    public void update(Integer id, UserResponse user) {
        // Lấy user đang đăng nhập
        UserResponse currentUser = getCurrentUser();

        // Kiểm tra quyền ADMIN
        if (!"ADMIN".equalsIgnoreCase(currentUser.getRole())) {
            throw new IllegalArgumentException("Bạn không có quyền cập nhật người dùng khác.");
        }

        // Lấy user trong DB
        User userDb = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Cập nhật thông tin
        userDb.setUsername(user.getUsername());
        userDb.setFullName(user.getFullName());
        userDb.setEmail(user.getEmail());
        userDb.setPhoneNumber(user.getPhoneNumber());
        userDb.setUpdatedAt(LocalDate.now().atStartOfDay());


        userRepo.save(userDb);
    }


    @Override
    public void updateRole(Integer id, String role) {
        UserResponse currentUser = getCurrentUser();

        if (!"ADMIN".equalsIgnoreCase(currentUser.getRole())) {
            throw new IllegalArgumentException("Bạn không có quyền cập nhật người dùng khác.");
        }

        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        User.Role newRole;
        try {
            newRole = User.Role.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Role không hợp lệ: " + role);
        }

        if (user.getRole() == newRole) {
            throw new IllegalArgumentException("Role đã là " + newRole.name());
        }

        user.setRole(newRole);
        user.setUpdatedAt(LocalDate.now().atStartOfDay());
        userRepo.save(user);
    }

    @Override
    public void delete(Integer id) {
        UserResponse currentUser = getCurrentUser();
        if (!"ADMIN".equalsIgnoreCase(currentUser.getRole())) {
            throw new IllegalArgumentException("Bạn không có quyền xóa người dùng khác.");
        }
        User user = userRepo.findById(id).
                orElseThrow(() -> new IllegalArgumentException("User not found")) ;
        user.setIsActive(false);
        userRepo.save(user);
    }


    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole().name())
                .build();
    }
}

