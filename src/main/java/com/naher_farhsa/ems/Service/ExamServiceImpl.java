package com.naher_farhsa.ems.Service;

import com.naher_farhsa.ems.Entity.Exam;
import com.naher_farhsa.ems.Entity.HallAllocation;
import com.naher_farhsa.ems.Entity.Student;
import com.naher_farhsa.ems.Enum.Course;
import com.naher_farhsa.ems.Enum.Hall;
import com.naher_farhsa.ems.Repository.ExamRepository;
import com.naher_farhsa.ems.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private HallAllocationServiceImpl hallAllocationServiceImpl;

    @Transactional
    public void addExam(Exam exam) {
        boolean courseFound = false;
        for (Course c : Course.values()) {
            if (c.name().equals(exam.getCourseId().getCourseCode())) {
                courseFound = true;
                break;
            }
        }
        if (!courseFound)
            throw new IllegalArgumentException("Invalid courseId: " + exam.getCourseId());

        boolean hallFound = false;
        for (Hall h : Hall.values()) {
            if (h.name().equals(exam.getHallId().name())) {
                hallFound = true;
                break;
            }
        }
        if (!hallFound)
            throw new IllegalArgumentException("Invalid courseId: " + exam.getCourseId());

        Exam addedExam = examRepository.save(exam);
        hallAllocationServiceImpl.addHallAllocationForExam(addedExam);
    }

    public List<Exam> getAllExams() {
        List<Exam> exams=examRepository.findAll();
        if (exams.isEmpty())
            throw new RuntimeException("No student found");
        return exams;
    }

    @Transactional
    public Exam updateExam(Exam exam) {
        Exam existingExam = examRepository.findById(exam.getExamId())
                .orElseThrow(() -> new RuntimeException("Exam not found with ID: " + exam.getExamId()));

        boolean isConflict = examRepository.existsByHallIdAndDateAndShiftAndExamIdNot(
                exam.getHallId(), exam.getDate(), exam.getShift(), exam.getExamId());

        if (isConflict) {
            throw new IllegalArgumentException("Exam with ID:"+exam.getExamId()+" is already scheduled with this hall, date, and shift.");
        }

        boolean updateHallAlloc = false;

        if (!existingExam.getHallId().equals(exam.getHallId())) {
            existingExam.setHallId(exam.getHallId());
            updateHallAlloc = true;
        }

        if (!existingExam.getDate().equals(exam.getDate())) {
            existingExam.setDate(exam.getDate());
            updateHallAlloc = true;
        }

        if (!existingExam.getShift().equals(exam.getShift())) {
            existingExam.setShift(exam.getShift());
            updateHallAlloc = true;
        }

        Exam savedExam = examRepository.save(existingExam);

        if (updateHallAlloc) {
            List<Student> enrolledStudents = studentRepository.findByEnrolledCourses(savedExam.getCourseId());
            hallAllocationServiceImpl.updateHallAllocationForExam(savedExam, enrolledStudents);
        }
        return savedExam;
    }

    @Transactional
    public void deleteExam(String examId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found with ID: " + examId));

        hallAllocationServiceImpl.deleteByExamId(examId); // delete related hall allocations
        examRepository.deleteById(examId);
    }


}
