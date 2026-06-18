package com.deltaclause.academy.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;

@Embeddable
public class SupportTicketReply {

    @Column(length = 30, nullable = false)
    private String sender; // 'student' | 'admin'

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    @Column(length = 50, nullable = false)
    private String timestamp;

    public SupportTicketReply() {}

    public SupportTicketReply(String sender, String message, String timestamp) {
        this.sender = sender;
        this.message = message;
        this.timestamp = timestamp;
    }

    // Getters & Setters
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
