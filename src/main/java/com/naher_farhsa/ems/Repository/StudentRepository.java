package com.naher_farhsa.ems.Repository;

import com.naher_farhsa.ems.Entity.Student;
import com.naher_farhsa.ems.Enum.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    // Custom query to find students by their enrolled course
    @Query("SELECT s FROM Student s WHERE :courseId MEMBER OF s.enrolledCourses")
    List<Student> findByEnrolledCourses(Course courseId);

}
