package com.logni.credit.service.service;

import com.logni.credit.service.exceptions.LoofiBusinessRunTimeException;
import com.logni.credit.service.model.LoanRepayment;
import com.logni.credit.service.repository.LoanRepaymentRepository;
import com.logni.credit.service.utilis.constants.CreditErrors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanRepaymentService {

   @Autowired
   private LoanRepaymentRepository loanRepaymentRepository;

   public LoanRepayment loanPayment(LoanRepayment loanRepaymentReceived) {
      Optional<LoanRepayment> loanRepayment = loanRepaymentRepository.findById(loanRepaymentReceived.getId());
      if (loanRepayment.isPresent()) {
          loanRepaymentReceived.setLoan(loanRepayment.get().getLoan());
//         loanRepayment.get().setStatus(loanRepaymentReceived.getStatus());
//         loanRepayment.get().setPaidAmount(loanRepaymentReceived.getPaidAmount());
         return loanRepaymentRepository.save(loanRepaymentReceived);
      }
      throw new LoofiBusinessRunTimeException(CreditErrors.getErrorCode(CreditErrors.LOGNI_CREDIT_SERVICE, CreditErrors.LOAN_REPAYMENT_NOT_FOUND),
            CreditErrors.ERROR_MAP.get(CreditErrors.LOAN_REPAYMENT_NOT_FOUND));
   }

    public List<LoanRepayment> fetchLoanPaymentsByLoanId(int loanId) {
      return loanRepaymentRepository.findByLoanId(loanId);
    }
}
