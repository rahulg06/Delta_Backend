package com.deltaclause.academy.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "support_tickets")
public class SupportTicket {

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
    @Column(nullable = false)
    private String subject;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    @NotBlank
    @Column(length = 30, nullable = false)
    private String status = "open"; // 'open' | 'resolved'

    @NotBlank
    @Column(name = "created_at", length = 50, nullable = false)
    private String createdAt;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "ticket_replies", joinColumns = @JoinColumn(name = "ticket_id"))
    @OrderColumn(name = "reply_order")
    private List<SupportTicketReply> replies = new ArrayList<>();

    public SupportTicket() {}

    public SupportTicket(String id, String studentEmail, String studentName, String subject, String message, String status, String createdAt) {
        this.id = id;
        this.studentEmail = studentEmail;
        this.studentName = studentName;
        this.subject = subject;
        this.message = message;
        this.status = status;
        this.createdAt = createdAt;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<SupportTicketReply> getReplies() {
        return replies;
    }

    public void setReplies(List<SupportTicketReply> replies) {
        this.replies = replies;
    }
}
