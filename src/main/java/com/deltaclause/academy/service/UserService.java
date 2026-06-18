package com.deltaclause.academy.service;

import com.deltaclause.academy.domain.Role;
import com.deltaclause.academy.domain.User;
import com.deltaclause.academy.domain.Referral;
import com.deltaclause.academy.repository.UserRepository;
import com.deltaclause.academy.repository.ReferralRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReferralRepository referralRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Looks up user by email or throws an error.
     */
    public User findByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new IllegalArgumentException("User with email " + email + " does not exist."));
    }

    /**
     * Registers a new student and checks if a referral code is associated with the registration.
     */
    @Transactional
    public User registerUser(String email, String name, String referralCodeApplied, String password) {
        String formattedEmail = email.trim().toLowerCase();
        
        if (userRepository.existsByEmailIgnoreCase(formattedEmail)) {
            throw new IllegalArgumentException("User already exists with email: " + formattedEmail);
        }

        // Initialize User Entity
        User newUser = new User();
        newUser.setEmail(formattedEmail);
        newUser.setName(name.trim());
        newUser.setRole(Role.ROLE_STUDENT);
        newUser.setReferralCode(formattedEmail); // Unique individual link code is their email
        newUser.setPoints(150); // Get standard signup points bonus
        newUser.setPassword(passwordEncoder.encode(password));

        User savedUser = userRepository.save(newUser);

        // Process referral invitation event if applicable
        if (referralCodeApplied != null && !referralCodeApplied.trim().isEmpty() && !referralCodeApplied.equalsIgnoreCase(formattedEmail)) {
            Optional<User> referrerOpt = userRepository.findByEmailIgnoreCase(referralCodeApplied.trim());
            if (referrerOpt.isPresent()) {
                User referrer = referrerOpt.get();
                
                // Add points to referrer for invitation success
                referrer.setPoints(referrer.getPoints() + 100); // 100 points reward for invitation join
                userRepository.save(referrer);

                // Create referral tracker entry
                Referral referralRecord = new Referral();
                referralRecord.setId("REF-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
                referralRecord.setReferrerEmail(referrer.getEmail());
                referralRecord.setReferredEmail(formattedEmail);
                referralRecord.setReferredName(newUser.getName());
                referralRecord.setSignupDate(Instant.now().toString());
                referralRecord.setStatus("joined");
                referralRecord.setRewardClaimed(false);

                referralRepository.save(referralRecord);
            }
        }

        return savedUser;
    }

    /**
     * Updates student points (useful for evaluation/reviews).
     */
    @Transactional
    public User updatePoints(String email, int pointsToAdd) {
        User user = findByEmail(email);
        user.setPoints(user.getPoints() + pointsToAdd);
        return userRepository.save(user);
    }
}
