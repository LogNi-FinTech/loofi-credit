package com.logni.credit.service.repository;

import com.logni.credit.service.model.LoanProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanProductRepository extends JpaRepository<LoanProduct, Integer>{

}
