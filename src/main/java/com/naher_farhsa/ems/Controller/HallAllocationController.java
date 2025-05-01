package com.naher_farhsa.ems.Controller;


import com.naher_farhsa.ems.DTO.HallAllocationDTO;
import com.naher_farhsa.ems.Entity.HallAllocation;
import com.naher_farhsa.ems.Service.HallAllocationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ems/hallAllocation")
public class HallAllocationController {

    @Autowired
    private HallAllocationServiceImpl hallAllocationServiceImpl;



 @GetMapping("/examCourse/{examCourseId}")
    public List<HallAllocationDTO> getHallAllocationByExamCourse(@PathVariable String examCourseId) {
        return hallAllocationServiceImpl.getHallAllocationByExamCourse(examCourseId);
    }

   /*
   // Get hall allocation for a specific student
    @GetMapping("/student/{studentId}")
    public List<HallAllocation> getHallAllocationByStudent(@PathVariable String studentId) {
        return hallAllocationService.getHallAllocationsByStudent(studentId);
    }
    */

}

