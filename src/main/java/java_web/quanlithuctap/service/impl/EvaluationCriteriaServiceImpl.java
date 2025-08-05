package java_web.quanlithuctap.service.impl;

import java_web.quanlithuctap.dto.EvaluationCriteriaDTO;
import java_web.quanlithuctap.entity.EvaluationCriteria;
import java_web.quanlithuctap.repository.EvaluationCriteriaRepository;
import java_web.quanlithuctap.service.EvaluationCriteriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
@RequiredArgsConstructor
public class EvaluationCriteriaServiceImpl implements EvaluationCriteriaService {

    private final EvaluationCriteriaRepository repository;

    @Override
    public List<EvaluationCriteriaDTO> getAll() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public EvaluationCriteriaDTO getById(Integer id) {
        return repository.findById(id).map(this::toDto).orElseThrow();
    }

    @Override
    public EvaluationCriteriaDTO create(EvaluationCriteriaDTO dto) {
        EvaluationCriteria entity = toEntity(dto);
        entity.setCreatedAt(LocalDate.now());
        entity.setUpdatedAt(LocalDate.now());
        return toDto(repository.save(entity));
    }

    @Override
    public void update(Integer id, EvaluationCriteriaDTO dto) {
        EvaluationCriteria criteria = repository.findById(id).orElseThrow();
        criteria.setCriterionName(dto.getCriterionName());
        criteria.setUpdatedAt(LocalDate.now());
        criteria.setDescription(dto.getDescription());
        criteria.setMaxScore(dto.getMaxScore());
        repository.save(criteria);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    private EvaluationCriteriaDTO toDto(EvaluationCriteria c) {
        EvaluationCriteriaDTO dto = new EvaluationCriteriaDTO();
        BeanUtils.copyProperties(c, dto);
        return dto;
    }

    private EvaluationCriteria toEntity(EvaluationCriteriaDTO dto) {
        EvaluationCriteria c = new EvaluationCriteria();
        BeanUtils.copyProperties(dto, c);
        return c;
    }
}
