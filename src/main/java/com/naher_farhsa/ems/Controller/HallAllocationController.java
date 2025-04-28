package com.naher_farhsa.ems.Controller;


import com.naher_farhsa.ems.Entity.HallAllocation;
import com.naher_farhsa.ems.Service.HallAllocationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ems/hallAllocation")
public class HallAllocationController {

    @Autowired
    private HallAllocationServiceImpl hallAllocationServiceImpl;

    @GetMapping("/getAll")
    public List<HallAllocation> getHallAllocationByExam() {
        return hallAllocationServiceImpl.getHallAllocations();
    }

 /*   @GetMapping("/exam/{examId}")
    public List<HallAllocation> getHallAllocationByExam(@PathVariable String examId) {
        return hallAllocationService.getHallAllocationsByExam(examId);
    }

    // Get hall allocation for a specific student
    @GetMapping("/student/{studentId}")
    public List<HallAllocation> getHallAllocationByStudent(@PathVariable String studentId) {
        return hallAllocationService.getHallAllocationsByStudent(studentId);
    }*/
}

