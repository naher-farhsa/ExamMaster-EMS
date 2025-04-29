package com.naher_farhsa.ems.Controller;

import com.naher_farhsa.ems.Entity.Exam;
import com.naher_farhsa.ems.Entity.Student;
import com.naher_farhsa.ems.Enum.Course;
import com.naher_farhsa.ems.Repository.ExamRepository;
import com.naher_farhsa.ems.Service.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ems/students")
public class StudentController {


    @Autowired
    private StudentServiceImpl studentServiceImpl;

    @Autowired
    private ExamRepository examRepository;

    @PostMapping("/add")
    public ResponseEntity<String> addStudent(@RequestBody Student student) {
        // Validate course codes
        List<Course> validCourses = new ArrayList<>();
        for (Course course : student.getEnrolledCourses()) {
            if (course == null) {
                throw new IllegalArgumentException("Course in the enrolled list cannot be null.");
            }
            boolean isValid = Arrays.asList(Course.values()).contains(course);
            if (!isValid) {
                throw new IllegalArgumentException("Invalid course code: " + course);
            }
            validCourses.add(course);
        }

        // Check if any matching exam exists for enrolled courses
        List<Exam> matchingExams = examRepository.findByCourseIdIn(validCourses);

        if (!matchingExams.isEmpty()) {
            // At least one course has an exam → Add student and allocate hall
            studentServiceImpl.addStudentAfterExam(student);

        } else {
            // No exam yet for enrolled courses → Only add student
           studentServiceImpl.addStudentBeforeExam(student);
        }
        return ResponseEntity.ok("Student with ID " + student.getStudentId() + " added successfully.");
    }



    @GetMapping("/getAll")
    public List<Student> getAllStudents() {
        return studentServiceImpl.getAllStudents();
    }

    @GetMapping("/getById/{studentId}")
    public Student getStudentById(@PathVariable String studentId) {
        return studentServiceImpl.getStudentById(studentId);
    }

    @DeleteMapping("/delete/{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable String studentId) {
        studentServiceImpl.deleteStudent(studentId);
        return ResponseEntity.ok("Student with ID " + studentId + " deleted successfully.");
    }

}

