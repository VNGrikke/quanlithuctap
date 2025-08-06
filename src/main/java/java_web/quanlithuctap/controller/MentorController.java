package java_web.quanlithuctap.controller;

import jakarta.validation.Valid;
import java_web.quanlithuctap.dto.MentorRequest;
import java_web.quanlithuctap.dto.MentorResponse;
import java_web.quanlithuctap.service.MentorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mentors")
@RequiredArgsConstructor
public class MentorController {

    private final MentorService mentorService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    public ResponseEntity<List<MentorResponse>> getAll() {
        return ResponseEntity.ok(mentorService.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MENTOR', 'ADMIN', 'STUDENT')")
    public ResponseEntity<MentorResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(mentorService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MentorResponse> createMentor(@RequestBody @Valid MentorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mentorService.createMentor(request));
    }

    @PutMapping("/{mentorId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
    public ResponseEntity<Void> updateMentor(@PathVariable Integer mentorId, @RequestBody @Valid MentorRequest request) {
        mentorService.updateMentor(mentorId, request);
        return ResponseEntity.noContent().build();
    }
}
