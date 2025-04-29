package com.naher_farhsa.ems.Entity;

import com.naher_farhsa.ems.Enum.Course;
import com.naher_farhsa.ems.Enum.Hall;
import com.naher_farhsa.ems.Enum.Shift;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "Exams")
public class Exam {

    @Id
    private String examId;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private Course courseId;

    @Column(nullable = false,unique = true)
    @Enumerated(EnumType.STRING)
    private Hall hallId;

    @Column(nullable = false, unique =true)
    private LocalDate date;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Shift shift;
}
