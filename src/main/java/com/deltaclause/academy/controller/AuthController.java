package com.deltaclause.academy.controller;

import com.deltaclause.academy.config.JwtTokenProvider;
import com.deltaclause.academy.domain.Role;
import com.deltaclause.academy.domain.User;
import com.deltaclause.academy.dto.AuthResponseDto;
import com.deltaclause.academy.dto.OtpRequestDto;
import com.deltaclause.academy.dto.OtpVerificationDto;
import com.deltaclause.academy.dto.SignInRequestDto;
import com.deltaclause.academy.repository.UserRepository;
import com.deltaclause.academy.service.EmailService;
import com.deltaclause.academy.service.OtpService;
import com.deltaclause.academy.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private OtpService otpService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Phase 1 of SignUp: Verify email & name, generate OTP, save inside Redis, and send email.
     */
    @PostMapping("/signup/otp-request")
    public ResponseEntity<String> requestSignUpOtp(@Valid @RequestBody OtpRequestDto requestDto) {
        String email = requestDto.getEmail().trim().toLowerCase();
        
        if (userRepository.existsByEmailIgnoreCase(email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: Email is already registered in our directory.");
        }

        // Generate Code & Save inside Redis with 5 min TTL
        String otp = otpService.generateAndSaveOtp(email);
        
        // Dispatch Email using SMTP Service (graces gracefully to mock if unsupported)
        emailService.sendOtpEmail(email, requestDto.getName(), otp);

        return ResponseEntity.ok("Verification passcode officially sent to " + email);
    }

    /**
     * Phase 2 of SignUp: Validate Redis OTP, create Hibernate standard User records, and issue token.
     */
    @PostMapping("/signup/otp-verify")
    public ResponseEntity<?> verifySignUpOtp(@Valid @RequestBody OtpVerificationDto verificationDto) {
        String email = verificationDto.getEmail().trim().toLowerCase();
        
        // Match from Redis Cache
        boolean isMatched = otpService.validateOtp(email, verificationDto.getCode());
        if (!isMatched) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Error: The verification code entered is invalid or has expired.");
        }

        // Create standard Hibernate JPA records
        try {
            User registeredUser = userService.registerUser(
                    email, 
                    verificationDto.getName(), 
                    verificationDto.getReferralCode(),
                    verificationDto.getPassword()
            );

            // Generate security authorization token
            String jwt = tokenProvider.generateToken(
                    registeredUser.getEmail(), 
                    registeredUser.getRole().name(), 
                    registeredUser.getName()
            );

            AuthResponseDto response = new AuthResponseDto(
                    jwt,
                    registeredUser.getEmail(),
                    registeredUser.getName(),
                    registeredUser.getRole().name(),
                    registeredUser.getPoints(),
                    registeredUser.getReferralCode()
            );

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + ex.getMessage());
        }
    }

    /**
     * Sign In controller supporting preloaded credentials and custom security verification.
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody SignInRequestDto loginDto) {
        String email = loginDto.getEmail().trim().toLowerCase();
        String pass = loginDto.getPassword();

        // 1. Core Default Admin clearances
        if (email.equals("rahulguptaendless@gmail.com") && pass.equals("rahul123")) {
            String jwt = tokenProvider.generateToken(email, Role.ROLE_ADMIN.name(), "Rahul Gupta");
            return ResponseEntity.ok(new AuthResponseDto(jwt, email, "Rahul Gupta", Role.ROLE_ADMIN.name(), 0, "rahul123"));
        }

        // 2. Preloaded Student clearance
        if (email.equals("vidolve@gmail.com") && pass.equals("vikas123")) {
            String jwt = tokenProvider.generateToken(email, Role.ROLE_STUDENT.name(), "Vikas Sharma");
            return ResponseEntity.ok(new AuthResponseDto(jwt, email, "Vikas Sharma", Role.ROLE_STUDENT.name(), 350, "vidolve@gmail.com"));
        }

        // 3. Registered student check
        Optional<User> userOpt = userRepository.findByEmailIgnoreCase(email);
        if (userOpt.isPresent()) {
            User existingUser = userOpt.get();
            // Verify encrypted passcodes using BCryptPasswordEncoder
            if (existingUser.getPassword() != null && passwordEncoder.matches(pass, existingUser.getPassword())) {
                String jwt = tokenProvider.generateToken(existingUser.getEmail(), existingUser.getRole().name(), existingUser.getName());
                return ResponseEntity.ok(new AuthResponseDto(
                        jwt,
                        existingUser.getEmail(),
                        existingUser.getName(),
                        existingUser.getRole().name(),
                        existingUser.getPoints(),
                        existingUser.getReferralCode()
                ));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Error: Access Clearance Rejected. Please verify your email and security credentials.");
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Error: Access Clearance Rejected. Please verify your email and security credentials.");
    }

    /**
     * Forgot Password Phase 1: Verify email, generate recovery OTP in Redis, dispatch HTML reset email.
     */
    @PostMapping("/forgot-password/otp-request")
    public ResponseEntity<String> requestForgotPasswordOtp(@RequestBody java.util.Map<String, String> request) {
        String email = request.get("email");
        if (email == null || email.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Email is required.");
        }
        email = email.trim().toLowerCase();

        Optional<User> userOpt = userRepository.findByEmailIgnoreCase(email);
        if (!userOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: No registered account matches the specified email address.");
        }

        User user = userOpt.get();
        // Generate secure 6-digit code and cache inside Redis
        String otp = otpService.generateAndSaveOtp(email);
        
        // Dispatch HTML reset notice
        emailService.sendPasswordResetOtpEmail(email, user.getName(), otp);

        return ResponseEntity.ok("Verification passcode for password reset sent to " + email);
    }

    /**
     * Forgot Password Phase 2: Validate code from Redis, update password in JPA/Hibernate repository.
     */
    @PostMapping("/forgot-password/otp-verify")
    public ResponseEntity<String> verifyForgotPasswordOtp(@RequestBody java.util.Map<String, String> request) {
        String email = request.get("email");
        String code = request.get("code");
        String newPassword = request.get("newPassword");

        if (email == null || code == null || newPassword == null || 
            email.trim().isEmpty() || code.trim().isEmpty() || newPassword.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Email, code, and newPassword are required parameters.");
        }
        email = email.trim().toLowerCase();

        // Validate OTP against Redis
        boolean isMatched = otpService.validateOtp(email, code.trim());
        if (!isMatched) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Error: The recovery OTP code has expired or is invalid.");
        }

        Optional<User> userOpt = userRepository.findByEmailIgnoreCase(email);
        if (!userOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: Account verification failed. User not found.");
        }

        User user = userOpt.get();
        user.setPassword(passwordEncoder.encode(newPassword.trim()));
        userRepository.save(user);

        return ResponseEntity.ok("Credentials updated successfully!");
    }
}
