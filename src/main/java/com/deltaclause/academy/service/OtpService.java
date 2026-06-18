package com.deltaclause.academy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

@Service
public class OtpService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String OTP_KEY_PREFIX = "OTP_ID:";
    private final SecureRandom secureRandom = new SecureRandom();

    /**
     * Generates a secure, 6-digit numerical OTP and stores it in Redis with a 5-minute TTL.
     */
    public String generateAndSaveOtp(String email) {
        String otp = String.format("%06d", secureRandom.nextInt(1000000));
        String redisKey = OTP_KEY_PREFIX + email.toLowerCase().trim();
        
        // Cache in Redis for exactly 5 minutes (300 seconds)
        redisTemplate.opsForValue().set(redisKey, otp, 5, TimeUnit.MINUTES);
        
        return otp;
    }

    /**
     * Validates if the supplied OTP matches the cached entry in Redis.
     */
    public boolean validateOtp(String email, String suppliedOtp) {
        String redisKey = OTP_KEY_PREFIX + email.toLowerCase().trim();
        String cachedOtp = redisTemplate.opsForValue().get(redisKey);
        
        if (cachedOtp != null && cachedOtp.equals(suppliedOtp)) {
            // Delete key from Redis immediately upon successful verification
            redisTemplate.delete(redisKey);
            return true;
        }
        return false;
    }
}
