package java_web.quanlithuctap.controller;

import jakarta.validation.Valid;
import java_web.quanlithuctap.dto.InternshipPhaseDTO;
import java_web.quanlithuctap.service.InternshipPhaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/internship_phases")
@RequiredArgsConstructor
public class InternshipPhaseController {

    private final InternshipPhaseService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'STUDENT')")
    public ResponseEntity<List<InternshipPhaseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'STUDENT')")
    public ResponseEntity<InternshipPhaseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InternshipPhaseDTO> create(@RequestBody @Valid InternshipPhaseDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody @Valid InternshipPhaseDTO dto) {
        service.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

