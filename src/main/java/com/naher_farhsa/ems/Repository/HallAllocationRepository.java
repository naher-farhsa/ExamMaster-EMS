package com.naher_farhsa.ems.Repository;

import com.naher_farhsa.ems.Entity.Exam;
import com.naher_farhsa.ems.Entity.HallAllocation;
import com.naher_farhsa.ems.Entity.Student;
import com.naher_farhsa.ems.Enum.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HallAllocationRepository extends JpaRepository<HallAllocation, String> {

    boolean existsByExamAndStudent(Exam exam, Student student);

    List<HallAllocation> findByExam_CourseId(Course course);

    void deleteByStudent_StudentId(String studentId);

    void deleteByExam_ExamId(String examId);
}
