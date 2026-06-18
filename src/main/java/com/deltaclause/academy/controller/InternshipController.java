package com.deltaclause.academy.controller;

import com.deltaclause.academy.domain.Internship;
import com.deltaclause.academy.repository.InternshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/internships")
public class InternshipController {

    @Autowired
    private InternshipRepository internshipRepository;

    /**
     * Public access catalog retrieval.
     */
    @GetMapping("/catalog")
    public ResponseEntity<List<Internship>> getCatalog() {
        return ResponseEntity.ok(internshipRepository.findAll());
    }

    /**
     * Add new industrial program (requires admin privileges).
     */
    @PostMapping("/admin/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Internship> createInternship(@RequestBody Internship internship) {
        return ResponseEntity.ok(internshipRepository.save(internship));
    }
}
