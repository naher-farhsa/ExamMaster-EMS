package com.naher_farhsa.ems.Repository;

import com.naher_farhsa.ems.Entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam,String> {
}
