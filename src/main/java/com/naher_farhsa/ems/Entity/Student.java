package com.naher_farhsa.ems.Entity;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Students")
public class Student {

    @Id
    private String studentId;

    private String studentName;

    @OneToOne
    private User user;

    @ElementCollection(fetch = FetchType.LAZY)  // Stores the list of courses in JSON format
    @Column(columnDefinition = "json", name = "enrolled_courses")  // Store as JSON in the database
    private List<String> enrolledCourses;
}

