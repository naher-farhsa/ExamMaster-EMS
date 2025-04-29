package com.naher_farhsa.ems.Repository;

import com.naher_farhsa.ems.Entity.Exam;
import com.naher_farhsa.ems.Enum.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam,String> {

    List<Exam> findByCourseIdIn(List<Course> courses);


}
