package com.logni.credit.service.controller;


import com.logni.credit.service.model.LoanProduct;
import com.logni.credit.service.repository.LoanProductRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api/product")
@Slf4j
public class LoanProductController {
	@Autowired
	private LoanProductRepository loanProductRepository;

	@SuppressWarnings("unchecked")
	@PostMapping
	ResponseEntity<LoanProduct> createLoanProduct(@RequestBody LoanProduct loanProductReceived){
		LoanProduct loanProduct = loanProductRepository.save(loanProductReceived);
		if (loanProduct != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(loanProduct);
		} else {
			return (ResponseEntity<LoanProduct>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping
	public ResponseEntity<List<LoanProduct>> fetchAllLoanProduct() {
		return ResponseEntity.ok().body(loanProductRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<LoanProduct> fetchLoanProductById(@PathVariable(value = "id") int loanProductId) {
		Optional<LoanProduct> loanProductOptional = loanProductRepository.findById(loanProductId);
		if (loanProductOptional.isPresent()) {
			return ResponseEntity.ok(loanProductOptional.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteLoanProductId(@PathVariable(value = "id") int loanProductId) {
		boolean exist = loanProductRepository.existsById(loanProductId);
		if (exist) {
			loanProductRepository.deleteById(loanProductId);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("resource deleted successfully");
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
