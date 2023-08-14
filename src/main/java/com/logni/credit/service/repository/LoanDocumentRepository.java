package com.logni.credit.service.repository;

import com.logni.credit.service.model.LoanDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanDocumentRepository extends JpaRepository<LoanDocument, Integer>{

}
