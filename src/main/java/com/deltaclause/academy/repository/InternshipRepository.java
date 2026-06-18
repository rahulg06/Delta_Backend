package com.deltaclause.academy.repository;

import com.deltaclause.academy.domain.Internship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InternshipRepository extends JpaRepository<Internship, String> {
    List<Internship> findByCategoryIgnoreCase(String category);
}
