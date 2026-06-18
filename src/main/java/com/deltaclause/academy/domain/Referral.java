package com.deltaclause.academy.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "referrals")
public class Referral {

    @Id
    @Column(length = 50, nullable = false)
    private String id;

    @NotBlank
    @Column(name = "referrer_email", length = 150, nullable = false)
    private String referrerEmail;

    @NotBlank
    @Column(name = "referred_email", length = 150, nullable = false)
    private String referredEmail;

    @NotBlank
    @Column(name = "referred_name", nullable = false)
    private String referredName;

    @NotBlank
    @Column(name = "signup_date", length = 50, nullable = false)
    private String signupDate;

    @NotBlank
    @Column(length = 30, nullable = false)
    private String status; // 'joined' | 'enrolled'

    @Column(name = "reward_claimed", nullable = false)
    private boolean rewardClaimed = false;

    public Referral() {}

    public Referral(String id, String referrerEmail, String referredEmail, String referredName, String signupDate, String status, boolean rewardClaimed) {
        this.id = id;
        this.referrerEmail = referrerEmail;
        this.referredEmail = referredEmail;
        this.referredName = referredName;
        this.signupDate = signupDate;
        this.status = status;
        this.rewardClaimed = rewardClaimed;
    }

    // Getters & Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReferrerEmail() {
        return referrerEmail;
    }

    public void setReferrerEmail(String referrerEmail) {
        this.referrerEmail = referrerEmail;
    }

    public String getReferredEmail() {
        return referredEmail;
    }

    public void setReferredEmail(String referredEmail) {
        this.referredEmail = referredEmail;
    }

    public String getReferredName() {
        return referredName;
    }

    public void setReferredName(String referredName) {
        this.referredName = referredName;
    }

    public String getSignupDate() {
        return signupDate;
    }

    public void setSignupDate(String signupDate) {
        this.signupDate = signupDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isRewardClaimed() {
        return rewardClaimed;
    }

    public void setRewardClaimed(boolean rewardClaimed) {
        this.rewardClaimed = rewardClaimed;
    }
}
