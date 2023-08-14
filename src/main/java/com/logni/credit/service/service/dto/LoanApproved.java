package com.logni.credit.service.service.dto;


import com.logni.credit.service.model.enumaration.LoanStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class LoanApproved {
	int loanId;
	int approvedBy;
	public LoanStatus loanStatus;
}
