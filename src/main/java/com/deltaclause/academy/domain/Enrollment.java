package com.deltaclause.academy.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "enrollments")
public class Enrollment {

    @Id
    @Column(length = 50, nullable = false)
    private String id;

    @NotBlank
    @Column(name = "student_email", length = 150, nullable = false)
    private String studentEmail;

    @NotBlank
    @Column(name = "student_name", nullable = false)
    private String studentName;

    @NotBlank
    @Column(name = "internship_id", length = 50, nullable = false)
    private String internshipId;

    @NotBlank
    @Column(length = 30, nullable = false)
    private String status; // payment_pending, payment_rejected, active, submitted, completed, failed, redo, expired

    @Column(name = "transaction_id", length = 100)
    private String transactionId;

    @Lob
    @Column(name = "payment_screenshot_url", columnDefinition = "LONGTEXT")
    private String paymentScreenshotUrl;

    @Column(name = "payment_timestamp", length = 50)
    private String paymentTimestamp;

    @Column(name = "submission_url", length = 512)
    private String submissionUrl;

    @Lob
    @Column(name = "submission_note", columnDefinition = "TEXT")
    private String submissionNote;

    @Column(name = "submission_timestamp", length = 50)
    private String submissionTimestamp;

    @Lob
    @Column(name = "admin_notes", columnDefinition = "TEXT")
    private String adminNotes;

    @Column(name = "completion_date", length = 50)
    private String completionDate;

    @Column(name = "expiry_date", length = 50)
    private String expiryDate;

    @Column(name = "certificate_id", length = 100)
    private String certificateId;

    @Column(name = "task_attempts", nullable = false)
    private int taskAttempts = 0;

    // Constructors
    public Enrollment() {}

    public Enrollment(String id, String studentEmail, String studentName, String internshipId, String status) {
        this.id = id;
        this.studentEmail = studentEmail;
        this.studentName = studentName;
        this.internshipId = internshipId;
        this.status = status;
    }

    // Getters & Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getInternshipId() {
        return internshipId;
    }

    public void setInternshipId(String internshipId) {
        this.internshipId = internshipId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPaymentScreenshotUrl() {
        return paymentScreenshotUrl;
    }

    public void setPaymentScreenshotUrl(String paymentScreenshotUrl) {
        this.paymentScreenshotUrl = paymentScreenshotUrl;
    }

    public String getPaymentTimestamp() {
        return paymentTimestamp;
    }

    public void setPaymentTimestamp(String paymentTimestamp) {
        this.paymentTimestamp = paymentTimestamp;
    }

    public String getSubmissionUrl() {
        return submissionUrl;
    }

    public void setSubmissionUrl(String submissionUrl) {
        this.submissionUrl = submissionUrl;
    }

    public String getSubmissionNote() {
        return submissionNote;
    }

    public void setSubmissionNote(String submissionNote) {
        this.submissionNote = submissionNote;
    }

    public String getSubmissionTimestamp() {
        return submissionTimestamp;
    }

    public void setSubmissionTimestamp(String submissionTimestamp) {
        this.submissionTimestamp = submissionTimestamp;
    }

    public String getAdminNotes() {
        return adminNotes;
    }

    public void setAdminNotes(String adminNotes) {
        this.adminNotes = adminNotes;
    }

    public String getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }

    public int getTaskAttempts() {
        return taskAttempts;
    }

    public void setTaskAttempts(int taskAttempts) {
        this.taskAttempts = taskAttempts;
    }
}
