package java_web.quanlithuctap.repository;

import java_web.quanlithuctap.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findByMentorId(Integer mentorId);
    Student findByUserId(Integer userId);
}
