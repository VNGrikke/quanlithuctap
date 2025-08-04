package java_web.quanlithuctap.service;

import java_web.quanlithuctap.dto.AssessmentRoundDTO;

import java.util.List;

public interface AssessmentRoundService {
    List<AssessmentRoundDTO> getAll(Integer phaseId);
    AssessmentRoundDTO getById(Integer id);
    AssessmentRoundDTO create(AssessmentRoundDTO dto);
    void update(Integer id, AssessmentRoundDTO dto);
}
