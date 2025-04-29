package com.naher_farhsa.ems.Controller;

import com.naher_farhsa.ems.Entity.Exam;
import com.naher_farhsa.ems.Enum.Course;
import com.naher_farhsa.ems.Service.ExamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ems/exams")
public class ExamController {

    @Autowired
    private ExamServiceImpl examServiceImpl;


    @PostMapping("/add")
    public ResponseEntity<String> addExam(@RequestBody Exam exam) {
        Course course = Course.valueOf(exam.getCourseId().toString());
        examServiceImpl.addExam(exam);
        return ResponseEntity.ok("Exam with ID " + exam.getExamId() + " added successfully.");
    }

}
