package java_web.quanlithuctap.controller;

import jakarta.validation.Valid;
import java_web.quanlithuctap.dto.LoginRequest;
import java_web.quanlithuctap.dto.RegisterRequest;
import java_web.quanlithuctap.dto.UserResponse;
import java_web.quanlithuctap.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String token = userService.login(request);
        return ResponseEntity.ok(Map.of("accessToken", token));
    }

    @PostMapping("/auth/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.status(201).body(userService.register(request));
    }

    @GetMapping("/auth/me")
    public ResponseEntity<UserResponse> getMe() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> getUser(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PutMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateUser(@PathVariable Integer id, @RequestBody @Valid UserResponse user) {
        userService.update(id, user);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateRole(@PathVariable Integer id,@RequestParam("role") String role) {
        userService.updateRole(id, role);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/{id}/active")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateActive(@PathVariable Integer id) {
        userService.update(id, userService.getById(id));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

