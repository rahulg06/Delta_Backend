package com.deltaclause.academy.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "certificates")
public class Certificate {

    @Id
    @Column(length = 100, nullable = false)
    private String id;

    @NotBlank
    @Column(name = "enrollment_id", length = 50, nullable = false)
    private String enrollmentId;

    @NotBlank
    @Column(name = "student_name", nullable = false)
    private String studentName;

    @NotBlank
    @Column(name = "internship_title", nullable = false)
    private String internshipTitle;

    @NotBlank
    @Column(name = "completion_date", length = 50, nullable = false)
    private String completionDate;

    @Column(name = "duration_weeks", nullable = false)
    private int durationWeeks;

    public Certificate() {}

    public Certificate(String id, String enrollmentId, String studentName, String internshipTitle, String completionDate, int durationWeeks) {
        this.id = id;
        this.enrollmentId = enrollmentId;
        this.studentName = studentName;
        this.internshipTitle = internshipTitle;
        this.completionDate = completionDate;
        this.durationWeeks = durationWeeks;
    }

    // Getters & Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(String enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getInternshipTitle() {
        return internshipTitle;
    }

    public void setInternshipTitle(String internshipTitle) {
        this.internshipTitle = internshipTitle;
    }

    public String getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    public int getDurationWeeks() {
        return durationWeeks;
    }

    public void setDurationWeeks(int durationWeeks) {
        this.durationWeeks = durationWeeks;
    }
}
