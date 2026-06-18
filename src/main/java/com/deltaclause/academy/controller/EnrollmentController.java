package com.deltaclause.academy.controller;

import com.deltaclause.academy.domain.Enrollment;
import com.deltaclause.academy.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private com.deltaclause.academy.repository.InternshipRepository internshipRepository;

    @Autowired
    private com.deltaclause.academy.service.EmailService emailService;

    /**
     * Lists all enrollments linked to the logged-in student.
     */
    @GetMapping("/my")
    public ResponseEntity<List<Enrollment>> getMyEnrollments(Principal principal) {
        String currentStudentEmail = principal.getName();
        return ResponseEntity.ok(enrollmentRepository.findByStudentEmailIgnoreCase(currentStudentEmail));
    }

    /**
     * Creates a new pending enrollment after UPI payment transaction details are entered.
     */
    @PostMapping("/apply")
    public ResponseEntity<Enrollment> applyForProgram(@RequestBody Enrollment request, Principal principal) {
        String studentEmail = principal.getName();

        Enrollment enrollment = new Enrollment();
        enrollment.setId("ENR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        enrollment.setStudentEmail(studentEmail);
        enrollment.setStudentName(request.getStudentName());
        enrollment.setInternshipId(request.getInternshipId());
        enrollment.setStatus("payment_pending");
        enrollment.setTransactionId(request.getTransactionId());
        enrollment.setPaymentScreenshotUrl(request.getPaymentScreenshotUrl());
        enrollment.setPaymentTimestamp(Instant.now().toString());
        enrollment.setTaskAttempts(0);

        return ResponseEntity.ok(enrollmentRepository.save(enrollment));
    }

    /**
     * Submits code repository links for evaluations.
     */
    @PostMapping("/{id}/submit")
    public ResponseEntity<?> submitProjectMilestone(@PathVariable String id, 
                                                    @RequestParam String submissionUrl,
                                                    @RequestParam String submissionNote,
                                                    Principal principal) {
        java.util.Optional<Enrollment> enrollmentOpt = enrollmentRepository.findById(id);
        if (!enrollmentOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Enrollment not found.");
        }
        
        Enrollment enrollment = enrollmentOpt.get();
        // Safety check ensuring student submits only their own files
        if (!enrollment.getStudentEmail().equalsIgnoreCase(principal.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
        }
        
        enrollment.setStatus("submitted");
        enrollment.setSubmissionUrl(submissionUrl);
        enrollment.setSubmissionNote(submissionNote);
        enrollment.setSubmissionTimestamp(Instant.now().toString());
        enrollment.setTaskAttempts(enrollment.getTaskAttempts() + 1);

        Enrollment saved = enrollmentRepository.save(enrollment);
        return ResponseEntity.ok(saved);
    }

    /**
     * Evaluates enrollment (Requires Admin role).
     */
    @PostMapping("/admin/{id}/evaluate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> evaluateEnrollment(@PathVariable String id,
                                                @RequestParam String status,
                                                @RequestParam(required = false) String adminNotes,
                                                @RequestParam(required = false) String certificateId) {
        java.util.Optional<Enrollment> enrollmentOpt = enrollmentRepository.findById(id);
        if (!enrollmentOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Enrollment not found.");
        }
        
        Enrollment enrollment = enrollmentOpt.get();
        String oldStatus = enrollment.getStatus();
        enrollment.setStatus(status);
        if (adminNotes != null) enrollment.setAdminNotes(adminNotes);
        
        com.deltaclause.academy.domain.Internship internship = internshipRepository.findById(enrollment.getInternshipId()).orElse(null);
        String internshipTitle = (internship != null) ? internship.getTitle() : "Deltaclause Industrial Program";
        
        if ("active".equalsIgnoreCase(status) && !"active".equalsIgnoreCase(oldStatus)) {
            emailService.sendOfferLetterEmail(enrollment.getStudentEmail(), enrollment.getStudentName(), internshipTitle);
        } else if ("completed".equalsIgnoreCase(status)) {
            enrollment.setCompletionDate(Instant.now().toString());
            String certId = certificateId;
            if (certId == null) {
                certId = "DC-CERT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            }
            enrollment.setCertificateId(certId);
            if (!"completed".equalsIgnoreCase(oldStatus)) {
                emailService.sendCertificateEmail(enrollment.getStudentEmail(), enrollment.getStudentName(), internshipTitle, certId);
            }
        } else if ("redo".equalsIgnoreCase(status)) {
            enrollment.setAdminNotes(adminNotes != null ? adminNotes : "Please review standard requirements and submit again.");
        }

        Enrollment saved = enrollmentRepository.save(enrollment);
        return ResponseEntity.ok(saved);
    }
}
