package com.deltaclause.academy.repository;

import com.deltaclause.academy.domain.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, String> {
    List<Enrollment> findByStudentEmailIgnoreCase(String studentEmail);
    Optional<Enrollment> findByCertificateId(String certificateId);
    List<Enrollment> findByStatus(String status);
}
