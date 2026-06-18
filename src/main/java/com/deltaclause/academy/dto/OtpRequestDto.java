package com.deltaclause.academy.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class OtpRequestDto {

    @Email
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Full name is required")
    private String name;

    private String referralCode; // Optional referral code applied during signup

    public OtpRequestDto() {}

    public OtpRequestDto(String email, String name, String referralCode) {
        this.email = email;
        this.name = name;
        this.referralCode = referralCode;
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

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }
}
