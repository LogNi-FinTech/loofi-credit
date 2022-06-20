package com.logni.credit.service.service.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LoanApplicationRequest {

    Integer loanProductId;

    Integer userId;

    Integer period;

    BigDecimal loanAmount;

    String name;

    String description;
}
