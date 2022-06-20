package com.logni.credit.service.repository;

import com.logni.credit.service.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Integer>{

}
