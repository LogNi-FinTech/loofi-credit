package com.logni.credit.service.controller;

import com.logni.credit.service.model.LoanRepayment;
import com.logni.credit.service.service.LoanRepaymentService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

   // todo partial repayment
}
