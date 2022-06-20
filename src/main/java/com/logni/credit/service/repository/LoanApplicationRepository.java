package com.logni.credit.service.repository;

import com.logni.credit.service.model.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Integer>{

}
