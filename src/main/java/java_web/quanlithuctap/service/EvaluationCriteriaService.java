package java_web.quanlithuctap.service;

import java_web.quanlithuctap.dto.EvaluationCriteriaDTO;

import java.util.List;

public interface EvaluationCriteriaService {
    List<EvaluationCriteriaDTO> getAll();
    EvaluationCriteriaDTO getById(Integer id);
    EvaluationCriteriaDTO create(EvaluationCriteriaDTO dto);
    void update(Integer id, EvaluationCriteriaDTO dto);
    void delete(Integer id);
}

