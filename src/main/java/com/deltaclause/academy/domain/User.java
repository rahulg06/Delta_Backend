package com.deltaclause.academy.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Email
    @NotBlank
    @Column(length = 150, nullable = false, unique = true)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private Role role;

    @Column(name = "referral_code", length = 100)
    private String referralCode;

    @Column(nullable = false)
    private int points = 0;

    @Column(length = 100)
    private String password;

    // No-arg constructor required by JPA
    public User() {}

    public User(String email, String name, Role role, String referralCode, int points) {
        this.email = email;
        this.name = name;
        this.role = role;
        this.referralCode = referralCode;
        this.points = points;
    }

    // Getters & Setters
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
