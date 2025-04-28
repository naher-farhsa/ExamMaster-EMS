package com.naher_farhsa.ems.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "HallAllocations")
public class HallAllocation {

    @Id
    private String hallAllocId;  // Unique identifier for the hall allocation (e.g., H1S1, H2S2)

    @ManyToOne
    @JoinColumn(name = "examId", referencedColumnName = "examId")
    private Exam exam;  // One-to-Many relation with Exam (multiple allocations can be related to one exam)

    @ManyToOne
    @JoinColumn(name = "studentId", referencedColumnName = "studentId")
    private Student student;  // Many-to-One relation with Stud ent (multiple students can be assigned to a hall allocation)

}
