package java_web.quanlithuctap.controller;

import jakarta.validation.Valid;
import java_web.quanlithuctap.dto.EvaluationCriteriaDTO;
import java_web.quanlithuctap.service.EvaluationCriteriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluation_criteria")
@RequiredArgsConstructor
public class EvaluationCriteriaController {

    private final EvaluationCriteriaService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'STUDENT')")
    public ResponseEntity<List<EvaluationCriteriaDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'STUDENT')")
    public ResponseEntity<EvaluationCriteriaDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EvaluationCriteriaDTO> create(@RequestBody @Valid EvaluationCriteriaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody @Valid EvaluationCriteriaDTO dto) {
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
