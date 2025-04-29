package com.naher_farhsa.ems.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HallAllocationDTO {
    private String hallAllocId;
    private String date;
    private String shift;
    private ExamInfo exam;
    private StudentInfo student;

    @Data
    @AllArgsConstructor
    public static class ExamInfo {
        private String examId;
        private String courseId;
        private String courseName;
    }

    @Data
    @AllArgsConstructor
    public static class StudentInfo {
        private String studentId;
        private String studentName;
    }
}
