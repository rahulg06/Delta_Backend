package com.deltaclause.academy.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "internships")
public class Internship {

    @Id
    @Column(length = 50, nullable = false)
    private String id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private double price;

    @Column(name = "duration_weeks", nullable = false)
    private int durationWeeks;

    @Column(name = "task_sheet_name", length = 150)
    private String taskSheetName;

    @Lob
    @Column(name = "task_sheet_pdf_url", columnDefinition = "LONGTEXT")
    private String taskSheetPdfUrl;

    @Column(name = "enrolled_count", nullable = false)
    private int enrolledCount = 0;

    @Column(length = 50)
    private String category;

    // Constructors
    public Internship() {}

    public Internship(String id, String title, String description, double price, int durationWeeks, String taskSheetName, String taskSheetPdfUrl, int enrolledCount, String category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.durationWeeks = durationWeeks;
        this.taskSheetName = taskSheetName;
        this.taskSheetPdfUrl = taskSheetPdfUrl;
        this.enrolledCount = enrolledCount;
        this.category = category;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDurationWeeks() {
        return durationWeeks;
    }

    public void setDurationWeeks(int durationWeeks) {
        this.durationWeeks = durationWeeks;
    }

    public String getTaskSheetName() {
        return taskSheetName;
    }

    public void setTaskSheetName(String taskSheetName) {
        this.taskSheetName = taskSheetName;
    }

    public String getTaskSheetPdfUrl() {
        return taskSheetPdfUrl;
    }

    public void setTaskSheetPdfUrl(String taskSheetPdfUrl) {
        this.taskSheetPdfUrl = taskSheetPdfUrl;
    }

    public int getEnrolledCount() {
        return enrolledCount;
    }

    public void setEnrolledCount(int enrolledCount) {
        this.enrolledCount = enrolledCount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
