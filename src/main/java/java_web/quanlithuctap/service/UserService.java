package java_web.quanlithuctap.service;

import java_web.quanlithuctap.dto.LoginRequest;
import java_web.quanlithuctap.dto.RegisterRequest;
import java_web.quanlithuctap.dto.UserResponse;

import java.util.List;

public interface UserService {
    String login(LoginRequest request);
    UserResponse register(RegisterRequest request);
    UserResponse getCurrentUser();
    List<UserResponse> getAll();
    UserResponse getById(Integer id);
    void update(Integer id, UserResponse user);
    void updateRole(Integer id, String role);
    void delete(Integer id);
}