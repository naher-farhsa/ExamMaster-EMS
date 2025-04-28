package com.naher_farhsa.ems.Controller;

import com.naher_farhsa.ems.Entity.Student;
import com.naher_farhsa.ems.Service.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ems/students")
public class StudentController {


    @Autowired
    private StudentServiceImpl studentServiceImpl;

    @PostMapping("/add")
    public Student addStudent(@RequestBody Student student) {
        return studentServiceImpl.addStudent(student);
    }

    @GetMapping("/getAll")
    public List<Student> getAllStudents() {
        return studentServiceImpl.getAllStudents();
    }

    @GetMapping("/getById/{studentId}")
    public Student getStudentById(@PathVariable String studentId) {
        return studentServiceImpl.getStudentById(studentId);
    }

    @PatchMapping("/updateEnrolledCourses/{studentId}")
    @ResponseBody
    public Student updateCourses(@PathVariable String studentId, @RequestBody List<String> newCourses) {
        return studentServiceImpl.updateEnrolledCourses(studentId, newCourses);
    }
}

