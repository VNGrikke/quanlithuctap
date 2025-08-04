package java_web.quanlithuctap.service;

import java_web.quanlithuctap.dto.InternshipPhaseDTO;

import java.util.List;

public interface InternshipPhaseService {
    List<InternshipPhaseDTO> getAll();
    InternshipPhaseDTO getById(Integer id);
    InternshipPhaseDTO create(InternshipPhaseDTO dto);
    void update(Integer id, InternshipPhaseDTO dto);
    void delete(Integer id);
}

