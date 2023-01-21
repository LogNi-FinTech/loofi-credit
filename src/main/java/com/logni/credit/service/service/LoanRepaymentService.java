package com.logni.credit.service.service;

import com.logni.credit.service.exceptions.LoofiBusinessRunTimeException;
import com.logni.credit.service.model.Loan;
import com.logni.credit.service.model.LoanRepayment;
import com.logni.credit.service.model.enumaration.Status;
import com.logni.credit.service.repository.LoanRepaymentRepository;
import com.logni.credit.service.repository.LoanRepository;
import com.logni.credit.service.utilis.constants.CreditErrors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class LoanRepaymentService {

   @Autowired
   private LoanRepaymentRepository loanRepaymentRepository;

    @Autowired
    private LoanRepository loanRepository;

   public LoanRepayment loanPayment(LoanRepayment loanRepaymentReceived) {
      Optional<LoanRepayment> loanRepayment = loanRepaymentRepository.findById(loanRepaymentReceived.getId());
      if (loanRepayment.isPresent()) {
          if(loanRepaymentReceived.getStatus().equals(Status.PARTIAL_PAID)){
              return partialPayment(loanRepaymentReceived, loanRepayment.get());
          }
          else {
              return fullPayment(loanRepayment.get());
          }
      }
      throw new LoofiBusinessRunTimeException(CreditErrors.getErrorCode(CreditErrors.LOGNI_CREDIT_SERVICE, CreditErrors.LOAN_REPAYMENT_NOT_FOUND),
            CreditErrors.ERROR_MAP.get(CreditErrors.LOAN_REPAYMENT_NOT_FOUND));
   }

    LoanRepayment partialPayment(LoanRepayment loanRepaymentReceived, LoanRepayment loanRepayment){
       if( loanRepayment.getUnPaidInterestAmount().compareTo(loanRepaymentReceived.getPaidInterestAmount()) >= 0 &&
               loanRepayment.getUnPaidPrincipleAmount().compareTo(loanRepaymentReceived.getPaidPrincipleAmount()) >= 0){
           Loan loan = loanRepayment.getLoan();
           loan.setUnpaidPrincipleAmount(loan.getUnpaidPrincipleAmount().subtract(loanRepaymentReceived.getPaidPrincipleAmount()));
           loan.setUnpaidInterestAmount(loan.getUnpaidInterestAmount().subtract(loanRepaymentReceived.getPaidInterestAmount()));
           loanRepository.save(loan);

           loanRepayment.setUnPaidInterestAmount(loanRepayment.getUnPaidInterestAmount().subtract(loanRepaymentReceived.getPaidInterestAmount()));
           loanRepayment.setUnPaidPrincipleAmount(loanRepayment.getUnPaidPrincipleAmount().subtract(loanRepaymentReceived.getPaidPrincipleAmount()));
           if(loanRepayment.getUnPaidPrincipleAmount().compareTo(new BigDecimal("0")) == 0 &&
                   loanRepayment.getUnPaidInterestAmount().compareTo(new BigDecimal("0")) == 0){
               loanRepayment.setStatus(Status.PAID);
           }
           else {
               loanRepayment.setStatus(Status.PARTIAL_PAID);
           }
           loanRepayment.setTxnId(loanRepaymentReceived.getTxnId());
           loanRepayment.setTxnNote(loanRepaymentReceived.getTxnNote());
           System.out.println(loanRepayment.getTxnId());
           return loanRepaymentRepository.save(loanRepayment);
       }
        throw new LoofiBusinessRunTimeException(CreditErrors.getErrorCode(CreditErrors.LOGNI_CREDIT_SERVICE, CreditErrors.LOAN_REPAYMENT_VALUE_INVALID),
                CreditErrors.ERROR_MAP.get(CreditErrors.LOAN_REPAYMENT_VALUE_INVALID));
   }

    LoanRepayment fullPayment(LoanRepayment loanRepayment){
        loanRepayment.setStatus(Status.PAID);
        Loan loan = loanRepayment.getLoan();
        loan.setUnpaidPrincipleAmount(loan.getUnpaidPrincipleAmount().subtract(loanRepayment.getPrincipleAmount()));
        loan.setUnpaidInterestAmount(loan.getUnpaidInterestAmount().subtract(loanRepayment.getInterestAmount()));
        loanRepository.save(loan);
        return loanRepaymentRepository.save(loanRepayment);
    }

    public List<LoanRepayment> fetchLoanPaymentsByLoanId(int loanId) {
      return loanRepaymentRepository.findByLoanId(loanId);
    }
}
