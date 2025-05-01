package com.naher_farhsa.ems.Controller;

import com.naher_farhsa.ems.Entity.Exam;
import com.naher_farhsa.ems.Enum.Course;
import com.naher_farhsa.ems.Service.ExamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ems/exams")
public class ExamController {

    @Autowired
    private ExamServiceImpl examServiceImpl;


    @PostMapping("/add")
    public ResponseEntity<String> addExam(@RequestBody Exam exam) {
      //  Course course = Course.valueOf(exam.getCourseId().toString());
        examServiceImpl.addExam(exam);
        return ResponseEntity.ok("Exam with ID " + exam.getExamId() + " added successfully.");
    }

    @GetMapping("/getAll")
    public List<Exam> getAllExams(){
        return examServiceImpl.getAllExams();
    }

    @PutMapping("/update")
    public Exam updateExam(@RequestBody Exam exam) {
       // Course course = Course.valueOf(exam.getCourseId().toString());
        return examServiceImpl.updateExam(exam);
    }

    @DeleteMapping("/delete/{examId}")
    public ResponseEntity<String> deleteExam(@PathVariable String examId) {
        examServiceImpl.deleteExam(examId);
        return ResponseEntity.ok("Exam with ID "+examId+" and related hall allocations deleted successfully.");
    }


}
