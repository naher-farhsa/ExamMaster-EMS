package com.naher_farhsa.ems.Service;

import com.naher_farhsa.ems.DTO.HallAllocationDTO;
import com.naher_farhsa.ems.Entity.Exam;
import com.naher_farhsa.ems.Entity.HallAllocation;
import com.naher_farhsa.ems.Entity.Student;
import com.naher_farhsa.ems.Enum.Hall;
import com.naher_farhsa.ems.Repository.HallAllocationRepository;
import com.naher_farhsa.ems.Repository.StudentRepository;
import com.naher_farhsa.ems.Service.HallAllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
public class HallAllocationServiceImpl implements HallAllocationService {

    @Autowired
    private HallAllocationRepository hallAllocationRepository;

    @Autowired
    private StudentRepository studentRepository;

    private static final int ROWS = 10;
    private static final int COLS = 10;

    public void addHallAllocationForExam(Exam exam) {
        List<Student> students = studentRepository.findByEnrolledCourses(exam.getCourseId());
        assignSets(exam, students, false); // false = skip already allocated students
    }

    @Transactional
    public void updateHallAllocationForExam(Exam exam, List<Student> students) {
        hallAllocationRepository.deleteByExam_ExamId(exam.getExamId());
        assignSets(exam, students, true); // true = assign all students afresh
    }


    private void assignSets(Exam exam, List<Student> students, boolean forceAllocate) {
        int studentIndex = 0;
        for (int row = 0; row < ROWS; row++) {
            boolean startWithA = (row % 2 == 0);
            for (int col = 0; col < COLS && studentIndex < students.size(); col++) {
                Student student = students.get(studentIndex);

                if (!forceAllocate && hallAllocationRepository.existsByExamAndStudent(exam, student)) {
                    studentIndex++;
                    continue;  // Skip if already allocated
                }

                String set = (startWithA == (col % 2 == 0)) ? "A" : "B";
                String hallAllocId = exam.getHallId().name() + set + student.getStudentId();

                HallAllocation hallAllocation = new HallAllocation();
                hallAllocation.setHallAllocId(hallAllocId);
                hallAllocation.setExam(exam);
                hallAllocation.setStudent(student);

                hallAllocationRepository.save(hallAllocation);
                studentIndex++;
            }
        }
    }

    public List<HallAllocationDTO> getHallAllocationByExam(String examId) {
        List<HallAllocation> hallAllocations = hallAllocationRepository.findByExam_ExamId(examId);
        List<HallAllocationDTO> hallAllocationDTOList = new ArrayList<>();

        for (HallAllocation alloc : hallAllocations) {
            HallAllocationDTO hallAllocationDTO = new HallAllocationDTO(
                    alloc.getHallAllocId(),
                    alloc.getExam().getDate().toString(),
                    alloc.getExam().getShift().name(),
                    new HallAllocationDTO.ExamInfo(
                            alloc.getExam().getExamId(),
                            alloc.getExam().getCourseId().getCourseCode(),
                            alloc.getExam().getCourseId().getCourseName()
                    ),
                    new HallAllocationDTO.StudentInfo(
                            alloc.getStudent().getStudentId(),
                            alloc.getStudent().getStudentName()
                    )
            );
            hallAllocationDTOList.add(hallAllocationDTO);
        }

        return hallAllocationDTOList;
    }
}
