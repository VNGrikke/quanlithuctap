package java_web.quanlithuctap.service.impl;

import java_web.quanlithuctap.dto.AssessmentRoundDTO;
import java_web.quanlithuctap.entity.AssessmentRound;
import java_web.quanlithuctap.entity.InternshipPhase;
import java_web.quanlithuctap.repository.AssessmentRoundRepository;
import java_web.quanlithuctap.repository.InternshipPhaseRepository;
import java_web.quanlithuctap.service.AssessmentRoundService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssessmentRoundServiceImpl implements AssessmentRoundService {

    private final AssessmentRoundRepository repository;
    private final InternshipPhaseRepository phaseRepository;

    @Override
    public List<AssessmentRoundDTO> getAll(Integer phaseId) {
        List<AssessmentRound> rounds = (phaseId == null)
                ? repository.findAll()
                : repository.findByPhase_PhaseId(phaseId);

        return rounds.stream().map(this::toDto).toList();
    }

    @Override
    public AssessmentRoundDTO getById(Integer id) {
        return repository.findById(id).map(this::toDto).orElseThrow();
    }

    @Override
    public AssessmentRoundDTO create(AssessmentRoundDTO dto) {
        InternshipPhase phase = phaseRepository.findById(dto.getPhaseId())
                .orElseThrow(() -> new IllegalArgumentException("Phase không tồn tại"));

        AssessmentRound round = AssessmentRound.builder()
                .phase(phase)
                .roundName(dto.getRoundName())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .description(dto.getDescription())
                .isActive(true)
                .build();

        return toDto(repository.save(round));
    }

    @Override
    public void update(Integer id, AssessmentRoundDTO dto) {
        AssessmentRound round = repository.findById(id).orElseThrow();

        round.setRoundName(dto.getRoundName());
        round.setStartDate(dto.getStartDate());
        round.setEndDate(dto.getEndDate());
        round.setDescription(dto.getDescription());
        round.setIsActive(dto.getIsActive());

        repository.save(round);
    }

    private AssessmentRoundDTO toDto(AssessmentRound r) {
        AssessmentRoundDTO dto = new AssessmentRoundDTO();
        dto.setRoundId(r.getRoundId());
        dto.setPhaseId(r.getPhase().getPhaseId());
        dto.setRoundName(r.getRoundName());
        dto.setStartDate(r.getStartDate());
        dto.setEndDate(r.getEndDate());
        dto.setDescription(r.getDescription());
        dto.setIsActive(r.getIsActive());
        return dto;
    }
}
