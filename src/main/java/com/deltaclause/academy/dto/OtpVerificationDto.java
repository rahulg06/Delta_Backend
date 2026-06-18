package com.deltaclause.academy.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class OtpVerificationDto {

    @Email
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Verification code is required")
    @Size(min = 6, max = 6, message = "OTP must be exactly 6 digits")
    private String code;

    @NotBlank(message = "Name is required")
    private String name;

    private String referralCode;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    public OtpVerificationDto() {}

    public OtpVerificationDto(String email, String code, String name, String referralCode, String password) {
        this.email = email;
        this.code = code;
        this.name = name;
        this.referralCode = referralCode;
        this.password = password;
    }

    // Getters & Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
