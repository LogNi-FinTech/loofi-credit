package com.logni.credit.service.service;

import com.logni.credit.service.model.Loan;
import com.logni.credit.service.model.LoanRepayment;
import com.logni.credit.service.model.enumaration.LoanStatus;
import com.logni.credit.service.model.enumaration.Status;
import com.logni.credit.service.repository.LoanRepaymentRepository;
import com.logni.credit.service.repository.LoanRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

@Service
@Slf4j
public class LoanService {

   @Autowired
   private LoanRepository loanRepository;

   @Autowired
   private LoanRepaymentRepository loanRepaymentRepository;

   public Loan createLoan(Loan loanReceived) {
      loanReceived.setUnpaidInterestAmount(loanReceived.getInterestAmount());
      loanReceived.setUnpaidPrincipleAmount(loanReceived.getPrincipleAmount());
      loanReceived.setLoanStatus(loanReceived.getLoanStatus());
      Loan loan = loanRepository.save(loanReceived);

      BigDecimal installmentPaymentPrincipleAmount, installmentPaymentInterestAmount, totalAmount;
      installmentPaymentPrincipleAmount = loan
            .getPrincipleAmount()
            .divide(new BigDecimal(String.valueOf(loan.getNoOfInstallment())), 6, RoundingMode.HALF_EVEN);
      ;
      installmentPaymentInterestAmount = loan
            .getInterestAmount()
            .divide(new BigDecimal(String.valueOf(loan.getNoOfInstallment())), 6, RoundingMode.HALF_EVEN);
      ;
      totalAmount = installmentPaymentPrincipleAmount.add(installmentPaymentInterestAmount);

      for (int i = 0; i < loan.getNoOfInstallment(); i++) {
         LoanRepayment loanRepayment = LoanRepayment
               .builder()
               .principleAmount(installmentPaymentPrincipleAmount)
               .unPaidPrincipleAmount(installmentPaymentPrincipleAmount)
               .interestAmount(installmentPaymentInterestAmount)
               .unPaidInterestAmount(installmentPaymentInterestAmount)
               .totalAmount(totalAmount)
               .paidAmount(null)
               .status(Status.UNPAID)
               .loan(loan)
               .build();
         loanRepaymentRepository.save(loanRepayment);
      }
      return loan;
   }

}
