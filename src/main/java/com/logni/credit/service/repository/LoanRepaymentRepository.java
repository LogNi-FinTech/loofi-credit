package com.logni.credit.service.repository;

import com.logni.credit.service.model.LoanRepayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepaymentRepository extends JpaRepository<LoanRepayment, Integer>{
    List<LoanRepayment> findByLoanId(int loanId);
}
