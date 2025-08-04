package java_web.quanlithuctap.service;

import java_web.quanlithuctap.dto.MentorRequest;
import java_web.quanlithuctap.dto.MentorResponse;

import java.util.List;

public interface MentorService {
    List<MentorResponse> getAll();
    MentorResponse getById(Integer id);
    MentorResponse createMentor(MentorRequest request);
    void updateMentor(Integer id, MentorRequest request);
}
