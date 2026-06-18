package com.deltaclause.academy.dto;

public class AuthResponseDto {

    private String token;
    private String email;
    private String name;
    private String role;
    private int points;
    private String referralCode;

    public AuthResponseDto() {}

    public AuthResponseDto(String token, String email, String name, String role, int points, String referralCode) {
        this.token = token;
        this.email = email;
        this.name = name;
        this.role = role;
        this.points = points;
        this.referralCode = referralCode;
    }

    // Getters & Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }
}
