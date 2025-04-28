package com.naher_farhsa.ems.Service;

import com.naher_farhsa.ems.Entity.Exam;
import com.naher_farhsa.ems.Entity.HallAllocation;
import com.naher_farhsa.ems.Enum.Course;
import com.naher_farhsa.ems.Enum.Hall;
import com.naher_farhsa.ems.Repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private HallAllocationServiceImpl hallAllocationServiceImpl;

    @Transactional
    public Exam addExam(Exam exam) {
        boolean isValidCourse = false;
        for (Course c : Course.values()) {
            if (c.name().equals(exam.getCourseId().name())) {
                isValidCourse = true;
                break;
            }
        }
        if (!isValidCourse) {
            throw new IllegalArgumentException("Invalid courseId : " + exam.getCourseId());
        }

        boolean isValidHall=false;
        for (Hall h : Hall.values()) {
            if (h.name().equals(exam.getHallId().name())) {
                isValidHall = true;
                break;
            }
        }
        if (!isValidHall) {
            throw new IllegalArgumentException("Invalid hallId : " +exam.getHallId() );
        }

        Exam addedExam = examRepository.save(exam);
        hallAllocationServiceImpl.addHallAllocationForExam(addedExam);
        return addedExam;
    }
}
