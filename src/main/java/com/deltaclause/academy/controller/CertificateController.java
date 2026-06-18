package com.deltaclause.academy.controller;

import com.deltaclause.academy.domain.Certificate;
import com.deltaclause.academy.repository.CertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/certificates")
public class CertificateController {

    @Autowired
    private CertificateRepository certificateRepository;

    /**
     * Public verification endpoint. Checks if ID matches verified records.
     */
    @GetMapping("/verify/{id}")
    public ResponseEntity<?> verifyCertificate(@PathVariable String id) {
        java.util.Optional<Certificate> certificateOpt = certificateRepository.findByIdIgnoreCase(id.trim());
        if (certificateOpt.isPresent()) {
            return ResponseEntity.ok(certificateOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: Certificate signature record with ID " + id + " could not be resolved.");
        }
    }

    /**
     * Issues new completion certificate record (Requires Admin role).
     */
    @PostMapping("/admin/issue")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Certificate> issueCertificate(@RequestBody Certificate certificate) {
        return ResponseEntity.ok(certificateRepository.save(certificate));
    }
}
