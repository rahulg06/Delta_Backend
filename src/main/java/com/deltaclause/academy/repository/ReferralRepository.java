package com.deltaclause.academy.repository;

import com.deltaclause.academy.domain.Referral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReferralRepository extends JpaRepository<Referral, String> {
    List<Referral> findByReferrerEmailIgnoreCase(String referrerEmail);
    Optional<Referral> findByReferredEmailIgnoreCase(String referredEmail);
}
