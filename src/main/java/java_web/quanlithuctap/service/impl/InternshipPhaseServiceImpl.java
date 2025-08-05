package java_web.quanlithuctap.service.impl;

import java_web.quanlithuctap.dto.InternshipPhaseDTO;
import java_web.quanlithuctap.entity.InternshipPhase;
import java_web.quanlithuctap.repository.InternshipPhaseRepository;
import java_web.quanlithuctap.service.InternshipPhaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InternshipPhaseServiceImpl implements InternshipPhaseService {

    private final InternshipPhaseRepository repository;

    @Override
    public List<InternshipPhaseDTO> getAll() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public InternshipPhaseDTO getById(Integer id) {
        return repository.findById(id).map(this::toDto).orElseThrow();
    }

    @Override
    public InternshipPhaseDTO create(InternshipPhaseDTO dto) {
        InternshipPhase entity = toEntity(dto);
        return toDto(repository.save(entity));
    }

    @Override
    public void update(Integer id, InternshipPhaseDTO dto) {
        InternshipPhase entity = repository.findById(id).orElseThrow();
        entity.setPhaseName(dto.getPhaseName());
        entity.setStartDate(dto.getStartDate());
        entity.setUpdatedAt(LocalDate.now());
        entity.setEndDate(dto.getEndDate());
        entity.setDescription(dto.getDescription());
        repository.save(entity);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    private InternshipPhaseDTO toDto(InternshipPhase phase) {
        InternshipPhaseDTO dto = new InternshipPhaseDTO();
        BeanUtils.copyProperties(phase, dto);
        return dto;
    }

    private InternshipPhase toEntity(InternshipPhaseDTO dto) {
        InternshipPhase phase = new InternshipPhase();
        phase.setCreatedAt(LocalDate.now());
        phase.setUpdatedAt(LocalDate.now());
        BeanUtils.copyProperties(dto, phase);
        return phase;
    }
}
