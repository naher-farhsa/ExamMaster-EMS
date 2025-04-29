package com.naher_farhsa.ems.Service;

import com.naher_farhsa.ems.Entity.Exam;
import com.naher_farhsa.ems.Entity.HallAllocation;
import com.naher_farhsa.ems.Entity.Student;
import com.naher_farhsa.ems.Enum.Course;
import com.naher_farhsa.ems.Repository.ExamRepository;
import com.naher_farhsa.ems.Repository.HallAllocationRepository;
import com.naher_farhsa.ems.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private HallAllocationRepository hallAllocationRepository;

    @Autowired
    private HallAllocationServiceImpl hallAllocationServiceImpl;

    public void addStudentBeforeExam(Student student) {
         studentRepository.save(student);
    }

    public void addStudentAfterExam(Student student) {
        Student savedStudent = studentRepository.save(student);
        List<Exam> existingExams = examRepository.findByCourseIdIn(savedStudent.getEnrolledCourses());
        for (Exam exam : existingExams) {
            hallAllocationServiceImpl.addHallAllocationForExam(exam);
        }

    }


    public List<Student> getAllStudents() {
        List<Student> students=studentRepository.findAll();
        if (students.isEmpty())
            throw new RuntimeException("No student found");
        return students;

    }

    public Student getStudentById(String studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));
    }

    @Transactional
    public void deleteStudent(String studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        if (student.isEmpty())
                throw new RuntimeException("Student with ID"+studentId+" not found");

        // Delete student (hall allocations will cascade if configured correctly)
        hallAllocationRepository.deleteByStudent_StudentId(studentId);
        studentRepository.deleteById(studentId);

        // Rebuild hall allocations for all exams related to this student
        List<Exam> affectedExams = examRepository.findByCourseIdIn(student.get().getEnrolledCourses());

        for (Exam exam : affectedExams) {
            List<Student> eligibleStudents = studentRepository.findByEnrolledCourses(exam.getCourseId());
            hallAllocationServiceImpl.updateHallAllocationForExam(exam, eligibleStudents);
        }
    }


}
