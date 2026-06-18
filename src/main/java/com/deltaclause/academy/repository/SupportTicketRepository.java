package com.deltaclause.academy.repository;

import com.deltaclause.academy.domain.SupportTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupportTicketRepository extends JpaRepository<SupportTicket, String> {
    List<SupportTicket> findByStudentEmailIgnoreCase(String studentEmail);
}
