package com.logni.credit.service.controller;

import com.logni.credit.service.model.Loan;
import com.logni.credit.service.repository.LoanRepository;
import com.logni.credit.service.service.LoanService;
import com.logni.credit.service.service.dto.LoanApproved;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api/loan")
@Slf4j
public class LoanController {

   @Autowired
   private LoanRepository loanRepository;

   @Autowired
   private LoanService loanService;

   @PostMapping
   ResponseEntity<Loan> createLoan(@Valid @RequestBody Loan loanReceived) {
      log.info(loanReceived.toString());
      Loan loan = loanService.createLoan(loanReceived);
      if (loan != null) {
         return ResponseEntity.status(HttpStatus.CREATED).body(loan);
      } else {
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
      }
   }

   @PostMapping("/approved")
   ResponseEntity<String> updateLoan(@RequestBody LoanApproved loanReceived) {
      Optional<Loan> createLoan = loanRepository.findById(loanReceived.getLoanId());
      if (createLoan.isPresent()) {
         if (createLoan.get().getLoanStatus().toString().equals("PENDING")) {
            createLoan.get().setApprovedBy(loanReceived.getApprovedBy());
            createLoan.get().setLoanStatus(loanReceived.getLoanStatus());
            loanRepository.save(createLoan.get());
            return ResponseEntity.status(HttpStatus.CREATED).body("Successfully Updated");
         }
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loan Is Rejected");
      }
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loan Not Found");
   }

   @GetMapping
   public ResponseEntity<List<Loan>> fetchAllLoan() {
      return ResponseEntity.ok().body(loanRepository.findAll());
   }

   @GetMapping("/{id}")
   public ResponseEntity<Loan> fetchLoanById(@PathVariable(value = "id") int loanId) {
      Optional<Loan> loanCreate = loanRepository.findById(loanId);
      return ResponseEntity.ok().body(loanCreate.get());
   }
}
