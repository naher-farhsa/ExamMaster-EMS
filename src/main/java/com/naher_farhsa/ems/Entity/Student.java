package com.naher_farhsa.ems.Entity;
import com.naher_farhsa.ems.Enum.Course;
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

    @ElementCollection(targetClass = Course.class, fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "student_enrolled_courses",
            joinColumns = @JoinColumn(name = "student_id")
    )
    @Column(name = "course")
    private List<Course> enrolledCourses;


}

