package com.naher_farhsa.ems.Service;

import com.naher_farhsa.ems.Entity.Student;
import com.naher_farhsa.ems.Enum.Course;
import com.naher_farhsa.ems.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public Student addStudent(Student student) {
        for (String courseCode : student.getEnrolledCourses()) {
            boolean isValidCourse = false;
            for (Course course : Course.values()) {
                if (course.getCourseCode().equals(courseCode)) {
                    isValidCourse = true;
                    break;
                }
            }
            if (!isValidCourse) {
                throw new IllegalArgumentException("Invalid course code: " + courseCode);
            }
        }
        return studentRepository.save(student);
    }


    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(String studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));
    }


    public Student updateEnrolledCourses(String studentId, List<String> newCourses) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        student.setEnrolledCourses(newCourses);
        return studentRepository.save(student);
    }
}
