package com.naher_farhsa.ems.controller;

import com.naher_farhsa.ems.Entity.Exam;
import com.naher_farhsa.ems.Service.ExamService;
import com.naher_farhsa.ems.Service.ExamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Exam addExam(@RequestBody Exam exam) {
        return examServiceImpl.addExam(exam);
    }

}
