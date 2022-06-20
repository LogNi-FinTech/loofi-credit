package com.logni.credit.service.model;

import com.logni.credit.service.model.enumaration.Frequency;
import com.logni.credit.service.model.enumaration.LoanStatus;

import lombok.*;

import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.math.BigDecimal;

@Table
@Entity
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Loan extends Auditable<String> {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   int id;

   @ManyToOne
   private LoanProduct loanProduct;

   @ManyToOne
   private LoanApplication loanApplication;

   @Enumerated(EnumType.STRING)
   @Type(type = "pgsql_enum")
   private Frequency frequency;

   @Enumerated(EnumType.STRING)
   @Type(type = "pgsql_enum")
   private LoanStatus loanStatus;

   String description;

   String note;

   String refTxn;

   Integer createdBy;

   Integer approvedBy;

   Integer noOfInstallment;

   BigDecimal principleAmount;

   BigDecimal interestAmount;

   BigDecimal interestRate;

}
