package java_web.quanlithuctap.controller;

import jakarta.validation.Valid;
import java_web.quanlithuctap.dto.AssessmentRoundDTO;
import java_web.quanlithuctap.service.AssessmentRoundService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assessment_rounds")
@RequiredArgsConstructor
public class AssessmentRoundController {

    private final AssessmentRoundService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'STUDENT')")
    public ResponseEntity<List<AssessmentRoundDTO>> getAll(@RequestParam(required = false) Integer phaseId) {
        return ResponseEntity.ok(service.getAll(phaseId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'STUDENT')")
    public ResponseEntity<AssessmentRoundDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AssessmentRoundDTO> create(@RequestBody @Valid AssessmentRoundDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody @Valid AssessmentRoundDTO dto) {
        service.update(id, dto);
        return ResponseEntity.noContent().build();
    }
}
