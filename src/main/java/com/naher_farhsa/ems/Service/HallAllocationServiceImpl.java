package com.naher_farhsa.ems.Service;

import com.naher_farhsa.ems.Entity.Exam;
import com.naher_farhsa.ems.Entity.HallAllocation;
import com.naher_farhsa.ems.Entity.Student;
import com.naher_farhsa.ems.Enum.Hall;
import com.naher_farhsa.ems.Repository.HallAllocationRepository;
import com.naher_farhsa.ems.Repository.StudentRepository;
import com.naher_farhsa.ems.Service.HallAllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public class HallAllocationServiceImpl implements HallAllocationService {

    @Autowired
    private HallAllocationRepository hallAllocationRepository;

    @Autowired
    private StudentRepository studentRepository;  // To fetch students for the course

    private static final int ROWS = 10;  // Define rows in the hall
    private static final int COLS = 10;  // Define columns in the hall

    public void addHallAllocationForExam(Exam exam) {
        // Fetch the list of students enrolled in the exam's course
        List<Student> students = studentRepository.findByEnrolledCourses(exam.getCourseId().getCourseCode());

        int studentIndex = 0;  // Track which student we are allocating

        // Loop over rows and columns to allocate seats
        for (int row = 0; row < ROWS; row++) {
            boolean startWithA1 = (row % 2 == 0);  // Even row -> start with A1
            for (int col = 0; col < COLS && studentIndex < students.size(); col++) {
                // Alternate between S1 and S2 based on row and column
                String seatSet = (startWithA1 == (col % 2 == 0)) ? "A1" : "A2";

                String hallAllocId =exam.getHallId().name() + seatSet + students.get(studentIndex).getStudentId();  // E.g., H1A1S1, H1A2S2
                HallAllocation hallAllocation = new HallAllocation();
                hallAllocation.setHallAllocId(hallAllocId);
                hallAllocation.setExam(exam);  // Set exam details
                hallAllocation.setStudent(students.get(studentIndex));  // Assign student to the hall

                // Save Hall Allocation
                hallAllocationRepository.save(hallAllocation);
                studentIndex++;  // Move to next student
            }
        }
    }
    // Get hall allocations
    public List<HallAllocation> getHallAllocations() {
        return hallAllocationRepository.findAll();
    }

 /*   // Get hall allocations by examId
    public List<> getHallAllocationsByExam(String examId) {
        return hallAllocationRepository.findByExamId(examId);
    }

    // Get hall allocations by studentId
    public List<HallAllocation> getHallAllocationsByStudent(String studentId) {
        return hallAllocationRepository.findByStudentId(studentId);
    }*/
}