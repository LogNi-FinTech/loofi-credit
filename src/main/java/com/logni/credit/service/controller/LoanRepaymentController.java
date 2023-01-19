package com.logni.credit.service.controller;

import com.logni.credit.service.model.LoanRepayment;
import com.logni.credit.service.service.LoanRepaymentService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan-repayment")
@Slf4j
public class LoanRepaymentController {

   @Autowired
   private LoanRepaymentService loanRepaymentService;

   @PostMapping("/payment")
   ResponseEntity<LoanRepayment> loanPayment(@RequestBody LoanRepayment loanRepaymentReceived) {
      LoanRepayment loanRepayment = loanRepaymentService.loanPayment(loanRepaymentReceived);
      //todo  when all installment done update main loan status
      if (loanRepayment != null) {
         return ResponseEntity.status(HttpStatus.CREATED).body(loanRepayment);
      } else {
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
      }
   }

   @GetMapping("/{loanId}")
   ResponseEntity<List<LoanRepayment>> fetchLoanPaymentsByLoanId(@PathVariable("loanId") int loanId) {
      List<LoanRepayment> loanRepayments = loanRepaymentService.fetchLoanPaymentsByLoanId(loanId);
      if (loanRepayments != null) {
         return ResponseEntity.status(HttpStatus.OK).body(loanRepayments);
      } else {
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
      }
   }

   // todo partial repayment
}
